package godxi.web.rest;

import godxi.GodxiApp;

import godxi.domain.OrderItem;
import godxi.repository.OrderItemRepository;
import godxi.service.OrderItemService;
import godxi.service.dto.OrderItemDTO;
import godxi.service.mapper.OrderItemMapper;
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
import java.math.BigDecimal;
import java.util.List;


import static godxi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderItemResource REST controller.
 *
 * @see OrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GodxiApp.class)
public class OrderItemResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ORIGINAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORIGINAL_PRICE = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_URL = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_URL = "BBBBBBBBBB";

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

    private static final BigDecimal DEFAULT_CURRENT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_PRODUCT_NUM = 1;
    private static final Integer UPDATED_PRODUCT_NUM = 2;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Autowired
    private OrderItemMapper orderItemMapper;
    

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderItemResource orderItemResource = new OrderItemResource(orderItemService);
        this.restOrderItemMockMvc = MockMvcBuilders.standaloneSetup(orderItemResource)
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
    public static OrderItem createEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .type(DEFAULT_TYPE)
            .materialId(DEFAULT_MATERIAL_ID)
            .name(DEFAULT_NAME)
            .originalPrice(DEFAULT_ORIGINAL_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .resourceUrl(DEFAULT_RESOURCE_URL)
            .deviceId(DEFAULT_DEVICE_ID)
            .lon(DEFAULT_LON)
            .lat(DEFAULT_LAT)
            .country(DEFAULT_COUNTRY)
            .province(DEFAULT_PROVINCE)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .address(DEFAULT_ADDRESS)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .productNum(DEFAULT_PRODUCT_NUM);
        return orderItem;
    }

    @Before
    public void initTest() {
        orderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderItem() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrderItem.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testOrderItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderItem.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testOrderItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrderItem.getResourceUrl()).isEqualTo(DEFAULT_RESOURCE_URL);
        assertThat(testOrderItem.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testOrderItem.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testOrderItem.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testOrderItem.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testOrderItem.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testOrderItem.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOrderItem.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testOrderItem.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrderItem.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testOrderItem.getProductNum()).isEqualTo(DEFAULT_PRODUCT_NUM);
    }

    @Test
    @Transactional
    public void createOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem with an existing ID
        orderItem.setId(1L);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setType(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaterialIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setMaterialId(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setName(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setOriginalPrice(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setDescription(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResourceUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setResourceUrl(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setDeviceId(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setLon(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setLat(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setCountry(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setProvince(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setCity(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setStreet(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setAddress(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setCurrentPrice(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemRepository.findAll().size();
        // set the field null
        orderItem.setProductNum(null);

        // Create the OrderItem, which fails.
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList
        restOrderItemMockMvc.perform(get("/api/order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].resourceUrl").value(hasItem(DEFAULT_RESOURCE_URL.toString())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].productNum").value(hasItem(DEFAULT_PRODUCT_NUM)));
    }
    

    @Test
    @Transactional
    public void getOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderItem.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.resourceUrl").value(DEFAULT_RESOURCE_URL.toString()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE.intValue()))
            .andExpect(jsonPath("$.productNum").value(DEFAULT_PRODUCT_NUM));
    }
    @Test
    @Transactional
    public void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem
        OrderItem updatedOrderItem = orderItemRepository.findById(orderItem.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItem are not directly saved in db
        em.detach(updatedOrderItem);
        updatedOrderItem
            .type(UPDATED_TYPE)
            .materialId(UPDATED_MATERIAL_ID)
            .name(UPDATED_NAME)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .description(UPDATED_DESCRIPTION)
            .resourceUrl(UPDATED_RESOURCE_URL)
            .deviceId(UPDATED_DEVICE_ID)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT)
            .country(UPDATED_COUNTRY)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .address(UPDATED_ADDRESS)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .productNum(UPDATED_PRODUCT_NUM);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(updatedOrderItem);

        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrderItem.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testOrderItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderItem.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testOrderItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrderItem.getResourceUrl()).isEqualTo(UPDATED_RESOURCE_URL);
        assertThat(testOrderItem.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testOrderItem.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testOrderItem.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testOrderItem.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testOrderItem.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testOrderItem.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOrderItem.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testOrderItem.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrderItem.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testOrderItem.getProductNum()).isEqualTo(UPDATED_PRODUCT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeDelete = orderItemRepository.findAll().size();

        // Get the orderItem
        restOrderItemMockMvc.perform(delete("/api/order-items/{id}", orderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItem.class);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(orderItem1.getId());
        assertThat(orderItem1).isEqualTo(orderItem2);
        orderItem2.setId(2L);
        assertThat(orderItem1).isNotEqualTo(orderItem2);
        orderItem1.setId(null);
        assertThat(orderItem1).isNotEqualTo(orderItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItemDTO.class);
        OrderItemDTO orderItemDTO1 = new OrderItemDTO();
        orderItemDTO1.setId(1L);
        OrderItemDTO orderItemDTO2 = new OrderItemDTO();
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
        orderItemDTO2.setId(orderItemDTO1.getId());
        assertThat(orderItemDTO1).isEqualTo(orderItemDTO2);
        orderItemDTO2.setId(2L);
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
        orderItemDTO1.setId(null);
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderItemMapper.fromId(null)).isNull();
    }
}
