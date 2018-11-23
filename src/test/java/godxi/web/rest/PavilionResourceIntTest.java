package godxi.web.rest;

import godxi.GodxiApp;

import godxi.domain.Pavilion;
import godxi.repository.PavilionRepository;
import godxi.service.PavilionService;
import godxi.service.dto.PavilionDTO;
import godxi.service.mapper.PavilionMapper;
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
 * Test class for the PavilionResource REST controller.
 *
 * @see PavilionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GodxiApp.class)
public class PavilionResourceIntTest {

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PavilionRepository pavilionRepository;


    @Autowired
    private PavilionMapper pavilionMapper;
    

    @Autowired
    private PavilionService pavilionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPavilionMockMvc;

    private Pavilion pavilion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PavilionResource pavilionResource = new PavilionResource(pavilionService);
        this.restPavilionMockMvc = MockMvcBuilders.standaloneSetup(pavilionResource)
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
    public static Pavilion createEntity(EntityManager em) {
        Pavilion pavilion = new Pavilion()
            .deviceId(DEFAULT_DEVICE_ID)
            .lon(DEFAULT_LON)
            .lat(DEFAULT_LAT)
            .country(DEFAULT_COUNTRY)
            .province(DEFAULT_PROVINCE)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .address(DEFAULT_ADDRESS)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return pavilion;
    }

    @Before
    public void initTest() {
        pavilion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPavilion() throws Exception {
        int databaseSizeBeforeCreate = pavilionRepository.findAll().size();

        // Create the Pavilion
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);
        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isCreated());

        // Validate the Pavilion in the database
        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeCreate + 1);
        Pavilion testPavilion = pavilionList.get(pavilionList.size() - 1);
        assertThat(testPavilion.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testPavilion.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testPavilion.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testPavilion.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPavilion.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testPavilion.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPavilion.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testPavilion.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPavilion.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPavilion.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createPavilionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pavilionRepository.findAll().size();

        // Create the Pavilion with an existing ID
        pavilion.setId(1L);
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pavilion in the database
        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setDeviceId(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setLon(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setLat(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setCountry(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setProvince(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setCity(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setStreet(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setAddress(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setCreateDate(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pavilionRepository.findAll().size();
        // set the field null
        pavilion.setUpdateDate(null);

        // Create the Pavilion, which fails.
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        restPavilionMockMvc.perform(post("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPavilions() throws Exception {
        // Initialize the database
        pavilionRepository.saveAndFlush(pavilion);

        // Get all the pavilionList
        restPavilionMockMvc.perform(get("/api/pavilions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pavilion.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getPavilion() throws Exception {
        // Initialize the database
        pavilionRepository.saveAndFlush(pavilion);

        // Get the pavilion
        restPavilionMockMvc.perform(get("/api/pavilions/{id}", pavilion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pavilion.getId().intValue()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPavilion() throws Exception {
        // Get the pavilion
        restPavilionMockMvc.perform(get("/api/pavilions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePavilion() throws Exception {
        // Initialize the database
        pavilionRepository.saveAndFlush(pavilion);

        int databaseSizeBeforeUpdate = pavilionRepository.findAll().size();

        // Update the pavilion
        Pavilion updatedPavilion = pavilionRepository.findById(pavilion.getId()).get();
        // Disconnect from session so that the updates on updatedPavilion are not directly saved in db
        em.detach(updatedPavilion);
        updatedPavilion
            .deviceId(UPDATED_DEVICE_ID)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT)
            .country(UPDATED_COUNTRY)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .address(UPDATED_ADDRESS)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        PavilionDTO pavilionDTO = pavilionMapper.toDto(updatedPavilion);

        restPavilionMockMvc.perform(put("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isOk());

        // Validate the Pavilion in the database
        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeUpdate);
        Pavilion testPavilion = pavilionList.get(pavilionList.size() - 1);
        assertThat(testPavilion.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testPavilion.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testPavilion.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testPavilion.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPavilion.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testPavilion.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPavilion.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testPavilion.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPavilion.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPavilion.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPavilion() throws Exception {
        int databaseSizeBeforeUpdate = pavilionRepository.findAll().size();

        // Create the Pavilion
        PavilionDTO pavilionDTO = pavilionMapper.toDto(pavilion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restPavilionMockMvc.perform(put("/api/pavilions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pavilionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pavilion in the database
        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePavilion() throws Exception {
        // Initialize the database
        pavilionRepository.saveAndFlush(pavilion);

        int databaseSizeBeforeDelete = pavilionRepository.findAll().size();

        // Get the pavilion
        restPavilionMockMvc.perform(delete("/api/pavilions/{id}", pavilion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pavilion> pavilionList = pavilionRepository.findAll();
        assertThat(pavilionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pavilion.class);
        Pavilion pavilion1 = new Pavilion();
        pavilion1.setId(1L);
        Pavilion pavilion2 = new Pavilion();
        pavilion2.setId(pavilion1.getId());
        assertThat(pavilion1).isEqualTo(pavilion2);
        pavilion2.setId(2L);
        assertThat(pavilion1).isNotEqualTo(pavilion2);
        pavilion1.setId(null);
        assertThat(pavilion1).isNotEqualTo(pavilion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PavilionDTO.class);
        PavilionDTO pavilionDTO1 = new PavilionDTO();
        pavilionDTO1.setId(1L);
        PavilionDTO pavilionDTO2 = new PavilionDTO();
        assertThat(pavilionDTO1).isNotEqualTo(pavilionDTO2);
        pavilionDTO2.setId(pavilionDTO1.getId());
        assertThat(pavilionDTO1).isEqualTo(pavilionDTO2);
        pavilionDTO2.setId(2L);
        assertThat(pavilionDTO1).isNotEqualTo(pavilionDTO2);
        pavilionDTO1.setId(null);
        assertThat(pavilionDTO1).isNotEqualTo(pavilionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pavilionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pavilionMapper.fromId(null)).isNull();
    }
}
