package godxi.web.rest;

import godxi.GodxiApp;

import godxi.domain.GodxiLicense;
import godxi.repository.GodxiLicenseRepository;
import godxi.service.GodxiLicenseService;
import godxi.service.dto.GodxiLicenseDTO;
import godxi.service.mapper.GodxiLicenseMapper;
import godxi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static godxi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GodxiLicenseResource REST controller.
 *
 * @see GodxiLicenseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GodxiApp.class)
public class GodxiLicenseResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_ISSUED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ISSUED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String DEFAULT_LICENSE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GodxiLicenseRepository godxiLicenseRepository;


    @Autowired
    private GodxiLicenseMapper godxiLicenseMapper;
    

    @Autowired
    private GodxiLicenseService godxiLicenseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGodxiLicenseMockMvc;

    private GodxiLicense godxiLicense;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GodxiLicenseResource godxiLicenseResource = new GodxiLicenseResource(godxiLicenseService);
        this.restGodxiLicenseMockMvc = MockMvcBuilders.standaloneSetup(godxiLicenseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GodxiLicense createEntity(EntityManager em) {
        GodxiLicense godxiLicense = new GodxiLicense()
            .email(DEFAULT_EMAIL)
            .deviceId(DEFAULT_DEVICE_ID)
            .issuedDate(DEFAULT_ISSUED_DATE)
            .expiredDate(DEFAULT_EXPIRED_DATE)
            .version(DEFAULT_VERSION)
            .valid(DEFAULT_VALID)
            .licenseKey(DEFAULT_LICENSE_KEY)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return godxiLicense;
    }

    @Before
    public void initTest() {
        godxiLicense = createEntity(em);
    }

    @Test
    @Transactional
    public void createGodxiLicense() throws Exception {
        int databaseSizeBeforeCreate = godxiLicenseRepository.findAll().size();

        // Create the GodxiLicense
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);
        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isCreated());

        // Validate the GodxiLicense in the database
        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeCreate + 1);
        GodxiLicense testGodxiLicense = godxiLicenseList.get(godxiLicenseList.size() - 1);
        assertThat(testGodxiLicense.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGodxiLicense.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testGodxiLicense.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testGodxiLicense.getExpiredDate()).isEqualTo(DEFAULT_EXPIRED_DATE);
        assertThat(testGodxiLicense.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGodxiLicense.isValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testGodxiLicense.getLicenseKey()).isEqualTo(DEFAULT_LICENSE_KEY);
        assertThat(testGodxiLicense.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testGodxiLicense.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createGodxiLicenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = godxiLicenseRepository.findAll().size();

        // Create the GodxiLicense with an existing ID
        godxiLicense.setId(1L);
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GodxiLicense in the database
        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setEmail(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setDeviceId(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setIssuedDate(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiredDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setExpiredDate(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setVersion(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setValid(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenseKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setLicenseKey(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setCreateDate(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = godxiLicenseRepository.findAll().size();
        // set the field null
        godxiLicense.setUpdateDate(null);

        // Create the GodxiLicense, which fails.
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        restGodxiLicenseMockMvc.perform(post("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGodxiLicenses() throws Exception {
        // Initialize the database
        godxiLicenseRepository.saveAndFlush(godxiLicense);

        // Get all the godxiLicenseList
        restGodxiLicenseMockMvc.perform(get("/api/godxi-licenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(godxiLicense.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(DEFAULT_ISSUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiredDate").value(hasItem(DEFAULT_EXPIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].licenseKey").value(hasItem(DEFAULT_LICENSE_KEY.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getGodxiLicense() throws Exception {
        // Initialize the database
        godxiLicenseRepository.saveAndFlush(godxiLicense);

        // Get the godxiLicense
        restGodxiLicenseMockMvc.perform(get("/api/godxi-licenses/{id}", godxiLicense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(godxiLicense.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.issuedDate").value(DEFAULT_ISSUED_DATE.toString()))
            .andExpect(jsonPath("$.expiredDate").value(DEFAULT_EXPIRED_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()))
            .andExpect(jsonPath("$.licenseKey").value(DEFAULT_LICENSE_KEY.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGodxiLicense() throws Exception {
        // Get the godxiLicense
        restGodxiLicenseMockMvc.perform(get("/api/godxi-licenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGodxiLicense() throws Exception {
        // Initialize the database
        godxiLicenseRepository.saveAndFlush(godxiLicense);

        int databaseSizeBeforeUpdate = godxiLicenseRepository.findAll().size();

        // Update the godxiLicense
        GodxiLicense updatedGodxiLicense = godxiLicenseRepository.findById(godxiLicense.getId()).get();
        // Disconnect from session so that the updates on updatedGodxiLicense are not directly saved in db
        em.detach(updatedGodxiLicense);
        updatedGodxiLicense
            .email(UPDATED_EMAIL)
            .deviceId(UPDATED_DEVICE_ID)
            .issuedDate(UPDATED_ISSUED_DATE)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .version(UPDATED_VERSION)
            .valid(UPDATED_VALID)
            .licenseKey(UPDATED_LICENSE_KEY)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(updatedGodxiLicense);

        restGodxiLicenseMockMvc.perform(put("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isOk());

        // Validate the GodxiLicense in the database
        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeUpdate);
        GodxiLicense testGodxiLicense = godxiLicenseList.get(godxiLicenseList.size() - 1);
        assertThat(testGodxiLicense.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGodxiLicense.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testGodxiLicense.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testGodxiLicense.getExpiredDate()).isEqualTo(UPDATED_EXPIRED_DATE);
        assertThat(testGodxiLicense.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGodxiLicense.isValid()).isEqualTo(UPDATED_VALID);
        assertThat(testGodxiLicense.getLicenseKey()).isEqualTo(UPDATED_LICENSE_KEY);
        assertThat(testGodxiLicense.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testGodxiLicense.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGodxiLicense() throws Exception {
        int databaseSizeBeforeUpdate = godxiLicenseRepository.findAll().size();

        // Create the GodxiLicense
        GodxiLicenseDTO godxiLicenseDTO = godxiLicenseMapper.toDto(godxiLicense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restGodxiLicenseMockMvc.perform(put("/api/godxi-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(godxiLicenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GodxiLicense in the database
        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGodxiLicense() throws Exception {
        // Initialize the database
        godxiLicenseRepository.saveAndFlush(godxiLicense);

        int databaseSizeBeforeDelete = godxiLicenseRepository.findAll().size();

        // Get the godxiLicense
        restGodxiLicenseMockMvc.perform(delete("/api/godxi-licenses/{id}", godxiLicense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GodxiLicense> godxiLicenseList = godxiLicenseRepository.findAll();
        assertThat(godxiLicenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GodxiLicense.class);
        GodxiLicense godxiLicense1 = new GodxiLicense();
        godxiLicense1.setId(1L);
        GodxiLicense godxiLicense2 = new GodxiLicense();
        godxiLicense2.setId(godxiLicense1.getId());
        assertThat(godxiLicense1).isEqualTo(godxiLicense2);
        godxiLicense2.setId(2L);
        assertThat(godxiLicense1).isNotEqualTo(godxiLicense2);
        godxiLicense1.setId(null);
        assertThat(godxiLicense1).isNotEqualTo(godxiLicense2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GodxiLicenseDTO.class);
        GodxiLicenseDTO godxiLicenseDTO1 = new GodxiLicenseDTO();
        godxiLicenseDTO1.setId(1L);
        GodxiLicenseDTO godxiLicenseDTO2 = new GodxiLicenseDTO();
        assertThat(godxiLicenseDTO1).isNotEqualTo(godxiLicenseDTO2);
        godxiLicenseDTO2.setId(godxiLicenseDTO1.getId());
        assertThat(godxiLicenseDTO1).isEqualTo(godxiLicenseDTO2);
        godxiLicenseDTO2.setId(2L);
        assertThat(godxiLicenseDTO1).isNotEqualTo(godxiLicenseDTO2);
        godxiLicenseDTO1.setId(null);
        assertThat(godxiLicenseDTO1).isNotEqualTo(godxiLicenseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(godxiLicenseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(godxiLicenseMapper.fromId(null)).isNull();
    }
}
