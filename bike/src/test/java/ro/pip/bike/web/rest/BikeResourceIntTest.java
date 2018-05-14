package ro.pip.bike.web.rest;

import ro.pip.bike.BikeApp;

import ro.pip.bike.domain.Bike;
import ro.pip.bike.repository.BikeRepository;
import ro.pip.bike.service.BikeService;
import ro.pip.bike.service.dto.BikeDTO;
import ro.pip.bike.service.mapper.BikeMapper;
import ro.pip.bike.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static ro.pip.bike.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BikeResource REST controller.
 *
 * @see BikeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BikeApp.class)
public class BikeResourceIntTest {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 5D;
    private static final Double UPDATED_PRICE = 6D;

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private BikeMapper bikeMapper;

    @Autowired
    private BikeService bikeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBikeMockMvc;

    private Bike bike;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BikeResource bikeResource = new BikeResource(bikeService);
        this.restBikeMockMvc = MockMvcBuilders.standaloneSetup(bikeResource)
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
    public static Bike createEntity(EntityManager em) {
        Bike bike = new Bike()
            .model(DEFAULT_MODEL)
            .price(DEFAULT_PRICE)
            .manufacturer(DEFAULT_MANUFACTURER);
        return bike;
    }

    @Before
    public void initTest() {
        bike = createEntity(em);
    }

    @Test
    @Transactional
    public void createBike() throws Exception {
        int databaseSizeBeforeCreate = bikeRepository.findAll().size();

        // Create the Bike
        BikeDTO bikeDTO = bikeMapper.toDto(bike);
        restBikeMockMvc.perform(post("/api/bikes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeDTO)))
            .andExpect(status().isCreated());

        // Validate the Bike in the database
        List<Bike> bikeList = bikeRepository.findAll();
        assertThat(bikeList).hasSize(databaseSizeBeforeCreate + 1);
        Bike testBike = bikeList.get(bikeList.size() - 1);
        assertThat(testBike.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testBike.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBike.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
    }

    @Test
    @Transactional
    public void createBikeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bikeRepository.findAll().size();

        // Create the Bike with an existing ID
        bike.setId(1L);
        BikeDTO bikeDTO = bikeMapper.toDto(bike);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBikeMockMvc.perform(post("/api/bikes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bike in the database
        List<Bike> bikeList = bikeRepository.findAll();
        assertThat(bikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bikeRepository.findAll().size();
        // set the field null
        bike.setPrice(null);

        // Create the Bike, which fails.
        BikeDTO bikeDTO = bikeMapper.toDto(bike);

        restBikeMockMvc.perform(post("/api/bikes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeDTO)))
            .andExpect(status().isBadRequest());

        List<Bike> bikeList = bikeRepository.findAll();
        assertThat(bikeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBikes() throws Exception {
        // Initialize the database
        bikeRepository.saveAndFlush(bike);

        // Get all the bikeList
        restBikeMockMvc.perform(get("/api/bikes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bike.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())));
    }

    @Test
    @Transactional
    public void getBike() throws Exception {
        // Initialize the database
        bikeRepository.saveAndFlush(bike);

        // Get the bike
        restBikeMockMvc.perform(get("/api/bikes/{id}", bike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bike.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBike() throws Exception {
        // Get the bike
        restBikeMockMvc.perform(get("/api/bikes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBike() throws Exception {
        // Initialize the database
        bikeRepository.saveAndFlush(bike);
        int databaseSizeBeforeUpdate = bikeRepository.findAll().size();

        // Update the bike
        Bike updatedBike = bikeRepository.findOne(bike.getId());
        // Disconnect from session so that the updates on updatedBike are not directly saved in db
        em.detach(updatedBike);
        updatedBike
            .model(UPDATED_MODEL)
            .price(UPDATED_PRICE)
            .manufacturer(UPDATED_MANUFACTURER);
        BikeDTO bikeDTO = bikeMapper.toDto(updatedBike);

        restBikeMockMvc.perform(put("/api/bikes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeDTO)))
            .andExpect(status().isOk());

        // Validate the Bike in the database
        List<Bike> bikeList = bikeRepository.findAll();
        assertThat(bikeList).hasSize(databaseSizeBeforeUpdate);
        Bike testBike = bikeList.get(bikeList.size() - 1);
        assertThat(testBike.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testBike.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBike.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    public void updateNonExistingBike() throws Exception {
        int databaseSizeBeforeUpdate = bikeRepository.findAll().size();

        // Create the Bike
        BikeDTO bikeDTO = bikeMapper.toDto(bike);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBikeMockMvc.perform(put("/api/bikes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeDTO)))
            .andExpect(status().isCreated());

        // Validate the Bike in the database
        List<Bike> bikeList = bikeRepository.findAll();
        assertThat(bikeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBike() throws Exception {
        // Initialize the database
        bikeRepository.saveAndFlush(bike);
        int databaseSizeBeforeDelete = bikeRepository.findAll().size();

        // Get the bike
        restBikeMockMvc.perform(delete("/api/bikes/{id}", bike.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bike> bikeList = bikeRepository.findAll();
        assertThat(bikeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bike.class);
        Bike bike1 = new Bike();
        bike1.setId(1L);
        Bike bike2 = new Bike();
        bike2.setId(bike1.getId());
        assertThat(bike1).isEqualTo(bike2);
        bike2.setId(2L);
        assertThat(bike1).isNotEqualTo(bike2);
        bike1.setId(null);
        assertThat(bike1).isNotEqualTo(bike2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BikeDTO.class);
        BikeDTO bikeDTO1 = new BikeDTO();
        bikeDTO1.setId(1L);
        BikeDTO bikeDTO2 = new BikeDTO();
        assertThat(bikeDTO1).isNotEqualTo(bikeDTO2);
        bikeDTO2.setId(bikeDTO1.getId());
        assertThat(bikeDTO1).isEqualTo(bikeDTO2);
        bikeDTO2.setId(2L);
        assertThat(bikeDTO1).isNotEqualTo(bikeDTO2);
        bikeDTO1.setId(null);
        assertThat(bikeDTO1).isNotEqualTo(bikeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bikeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bikeMapper.fromId(null)).isNull();
    }
}
