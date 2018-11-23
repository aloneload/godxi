package godxi.web.rest;

import godxi.GodxiApp;

import godxi.domain.Work;
import godxi.repository.WorkRepository;
import godxi.service.WorkService;
import godxi.service.dto.WorkDTO;
import godxi.service.mapper.WorkMapper;
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
 * Test class for the WorkResource REST controller.
 *
 * @see WorkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GodxiApp.class)
public class WorkResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_URL = "AAAAAAAAAA";
    private static final String UPDATED_COVER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DURATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DURATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CATEGORIES = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIES = "BBBBBBBBBB";

    private static final String DEFAULT_LABELS = "AAAAAAAAAA";
    private static final String UPDATED_LABELS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WorkRepository workRepository;


    @Autowired
    private WorkMapper workMapper;
    

    @Autowired
    private WorkService workService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkMockMvc;

    private Work work;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkResource workResource = new WorkResource(workService);
        this.restWorkMockMvc = MockMvcBuilders.standaloneSetup(workResource)
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
    public static Work createEntity(EntityManager em) {
        Work work = new Work()
            .userId(DEFAULT_USER_ID)
            .name(DEFAULT_NAME)
            .title(DEFAULT_TITLE)
            .coverUrl(DEFAULT_COVER_URL)
            .videoUrl(DEFAULT_VIDEO_URL)
            .duration(DEFAULT_DURATION)
            .categories(DEFAULT_CATEGORIES)
            .labels(DEFAULT_LABELS)
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return work;
    }

    @Before
    public void initTest() {
        work = createEntity(em);
    }

    @Test
    @Transactional
    public void createWork() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isCreated());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate + 1);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testWork.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWork.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testWork.getCoverUrl()).isEqualTo(DEFAULT_COVER_URL);
        assertThat(testWork.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testWork.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testWork.getCategories()).isEqualTo(DEFAULT_CATEGORIES);
        assertThat(testWork.getLabels()).isEqualTo(DEFAULT_LABELS);
        assertThat(testWork.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWork.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testWork.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work with an existing ID
        work.setId(1L);
        WorkDTO workDTO = workMapper.toDto(work);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setUserId(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setName(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setTitle(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCoverUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setCoverUrl(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVideoUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setVideoUrl(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setDuration(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoriesIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setCategories(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelsIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setLabels(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setDescription(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setCreateDate(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setUpdateDate(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorks() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get all the workList
        restWorkMockMvc.perform(get("/api/works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(work.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].coverUrl").value(hasItem(DEFAULT_COVER_URL.toString())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].categories").value(hasItem(DEFAULT_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].labels").value(hasItem(DEFAULT_LABELS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", work.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(work.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.coverUrl").value(DEFAULT_COVER_URL.toString()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.categories").value(DEFAULT_CATEGORIES.toString()))
            .andExpect(jsonPath("$.labels").value(DEFAULT_LABELS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingWork() throws Exception {
        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Update the work
        Work updatedWork = workRepository.findById(work.getId()).get();
        // Disconnect from session so that the updates on updatedWork are not directly saved in db
        em.detach(updatedWork);
        updatedWork
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .coverUrl(UPDATED_COVER_URL)
            .videoUrl(UPDATED_VIDEO_URL)
            .duration(UPDATED_DURATION)
            .categories(UPDATED_CATEGORIES)
            .labels(UPDATED_LABELS)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        WorkDTO workDTO = workMapper.toDto(updatedWork);

        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isOk());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testWork.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWork.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWork.getCoverUrl()).isEqualTo(UPDATED_COVER_URL);
        assertThat(testWork.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testWork.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testWork.getCategories()).isEqualTo(UPDATED_CATEGORIES);
        assertThat(testWork.getLabels()).isEqualTo(UPDATED_LABELS);
        assertThat(testWork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWork.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testWork.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWork() throws Exception {
        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        int databaseSizeBeforeDelete = workRepository.findAll().size();

        // Get the work
        restWorkMockMvc.perform(delete("/api/works/{id}", work.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Work.class);
        Work work1 = new Work();
        work1.setId(1L);
        Work work2 = new Work();
        work2.setId(work1.getId());
        assertThat(work1).isEqualTo(work2);
        work2.setId(2L);
        assertThat(work1).isNotEqualTo(work2);
        work1.setId(null);
        assertThat(work1).isNotEqualTo(work2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDTO.class);
        WorkDTO workDTO1 = new WorkDTO();
        workDTO1.setId(1L);
        WorkDTO workDTO2 = new WorkDTO();
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO2.setId(workDTO1.getId());
        assertThat(workDTO1).isEqualTo(workDTO2);
        workDTO2.setId(2L);
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO1.setId(null);
        assertThat(workDTO1).isNotEqualTo(workDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workMapper.fromId(null)).isNull();
    }
}
