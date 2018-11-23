package godxi.web.rest;

import godxi.GodxiApp;

import godxi.domain.Backpack;
import godxi.repository.BackpackRepository;
import godxi.service.BackpackService;
import godxi.service.dto.BackpackDTO;
import godxi.service.mapper.BackpackMapper;
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
 * Test class for the BackpackResource REST controller.
 *
 * @see BackpackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GodxiApp.class)
public class BackpackResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_TOTAL_NUM = 1;
    private static final Integer UPDATED_TOTAL_NUM = 2;

    private static final Integer DEFAULT_USED_NUM = 1;
    private static final Integer UPDATED_USED_NUM = 2;

    private static final Integer DEFAULT_UNUSED_NUM = 1;
    private static final Integer UPDATED_UNUSED_NUM = 2;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BackpackRepository backpackRepository;


    @Autowired
    private BackpackMapper backpackMapper;
    

    @Autowired
    private BackpackService backpackService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBackpackMockMvc;

    private Backpack backpack;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BackpackResource backpackResource = new BackpackResource(backpackService);
        this.restBackpackMockMvc = MockMvcBuilders.standaloneSetup(backpackResource)
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
    public static Backpack createEntity(EntityManager em) {
        Backpack backpack = new Backpack()
            .userId(DEFAULT_USER_ID)
            .totalNum(DEFAULT_TOTAL_NUM)
            .usedNum(DEFAULT_USED_NUM)
            .unusedNum(DEFAULT_UNUSED_NUM)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return backpack;
    }

    @Before
    public void initTest() {
        backpack = createEntity(em);
    }

    @Test
    @Transactional
    public void createBackpack() throws Exception {
        int databaseSizeBeforeCreate = backpackRepository.findAll().size();

        // Create the Backpack
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);
        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isCreated());

        // Validate the Backpack in the database
        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeCreate + 1);
        Backpack testBackpack = backpackList.get(backpackList.size() - 1);
        assertThat(testBackpack.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBackpack.getTotalNum()).isEqualTo(DEFAULT_TOTAL_NUM);
        assertThat(testBackpack.getUsedNum()).isEqualTo(DEFAULT_USED_NUM);
        assertThat(testBackpack.getUnusedNum()).isEqualTo(DEFAULT_UNUSED_NUM);
        assertThat(testBackpack.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testBackpack.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createBackpackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = backpackRepository.findAll().size();

        // Create the Backpack with an existing ID
        backpack.setId(1L);
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Backpack in the database
        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = backpackRepository.findAll().size();
        // set the field null
        backpack.setUserId(null);

        // Create the Backpack, which fails.
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = backpackRepository.findAll().size();
        // set the field null
        backpack.setTotalNum(null);

        // Create the Backpack, which fails.
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsedNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = backpackRepository.findAll().size();
        // set the field null
        backpack.setUsedNum(null);

        // Create the Backpack, which fails.
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnusedNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = backpackRepository.findAll().size();
        // set the field null
        backpack.setUnusedNum(null);

        // Create the Backpack, which fails.
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = backpackRepository.findAll().size();
        // set the field null
        backpack.setCreateDate(null);

        // Create the Backpack, which fails.
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = backpackRepository.findAll().size();
        // set the field null
        backpack.setUpdateDate(null);

        // Create the Backpack, which fails.
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        restBackpackMockMvc.perform(post("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBackpacks() throws Exception {
        // Initialize the database
        backpackRepository.saveAndFlush(backpack);

        // Get all the backpackList
        restBackpackMockMvc.perform(get("/api/backpacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backpack.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].totalNum").value(hasItem(DEFAULT_TOTAL_NUM)))
            .andExpect(jsonPath("$.[*].usedNum").value(hasItem(DEFAULT_USED_NUM)))
            .andExpect(jsonPath("$.[*].unusedNum").value(hasItem(DEFAULT_UNUSED_NUM)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getBackpack() throws Exception {
        // Initialize the database
        backpackRepository.saveAndFlush(backpack);

        // Get the backpack
        restBackpackMockMvc.perform(get("/api/backpacks/{id}", backpack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(backpack.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.totalNum").value(DEFAULT_TOTAL_NUM))
            .andExpect(jsonPath("$.usedNum").value(DEFAULT_USED_NUM))
            .andExpect(jsonPath("$.unusedNum").value(DEFAULT_UNUSED_NUM))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBackpack() throws Exception {
        // Get the backpack
        restBackpackMockMvc.perform(get("/api/backpacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBackpack() throws Exception {
        // Initialize the database
        backpackRepository.saveAndFlush(backpack);

        int databaseSizeBeforeUpdate = backpackRepository.findAll().size();

        // Update the backpack
        Backpack updatedBackpack = backpackRepository.findById(backpack.getId()).get();
        // Disconnect from session so that the updates on updatedBackpack are not directly saved in db
        em.detach(updatedBackpack);
        updatedBackpack
            .userId(UPDATED_USER_ID)
            .totalNum(UPDATED_TOTAL_NUM)
            .usedNum(UPDATED_USED_NUM)
            .unusedNum(UPDATED_UNUSED_NUM)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        BackpackDTO backpackDTO = backpackMapper.toDto(updatedBackpack);

        restBackpackMockMvc.perform(put("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isOk());

        // Validate the Backpack in the database
        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeUpdate);
        Backpack testBackpack = backpackList.get(backpackList.size() - 1);
        assertThat(testBackpack.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBackpack.getTotalNum()).isEqualTo(UPDATED_TOTAL_NUM);
        assertThat(testBackpack.getUsedNum()).isEqualTo(UPDATED_USED_NUM);
        assertThat(testBackpack.getUnusedNum()).isEqualTo(UPDATED_UNUSED_NUM);
        assertThat(testBackpack.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testBackpack.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBackpack() throws Exception {
        int databaseSizeBeforeUpdate = backpackRepository.findAll().size();

        // Create the Backpack
        BackpackDTO backpackDTO = backpackMapper.toDto(backpack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restBackpackMockMvc.perform(put("/api/backpacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backpackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Backpack in the database
        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBackpack() throws Exception {
        // Initialize the database
        backpackRepository.saveAndFlush(backpack);

        int databaseSizeBeforeDelete = backpackRepository.findAll().size();

        // Get the backpack
        restBackpackMockMvc.perform(delete("/api/backpacks/{id}", backpack.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Backpack> backpackList = backpackRepository.findAll();
        assertThat(backpackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Backpack.class);
        Backpack backpack1 = new Backpack();
        backpack1.setId(1L);
        Backpack backpack2 = new Backpack();
        backpack2.setId(backpack1.getId());
        assertThat(backpack1).isEqualTo(backpack2);
        backpack2.setId(2L);
        assertThat(backpack1).isNotEqualTo(backpack2);
        backpack1.setId(null);
        assertThat(backpack1).isNotEqualTo(backpack2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackpackDTO.class);
        BackpackDTO backpackDTO1 = new BackpackDTO();
        backpackDTO1.setId(1L);
        BackpackDTO backpackDTO2 = new BackpackDTO();
        assertThat(backpackDTO1).isNotEqualTo(backpackDTO2);
        backpackDTO2.setId(backpackDTO1.getId());
        assertThat(backpackDTO1).isEqualTo(backpackDTO2);
        backpackDTO2.setId(2L);
        assertThat(backpackDTO1).isNotEqualTo(backpackDTO2);
        backpackDTO1.setId(null);
        assertThat(backpackDTO1).isNotEqualTo(backpackDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(backpackMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(backpackMapper.fromId(null)).isNull();
    }
}
