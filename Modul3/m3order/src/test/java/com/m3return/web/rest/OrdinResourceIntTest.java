package com.m3return.web.rest;

import com.m3return.M3OrderApp;

import com.m3return.domain.Ordin;
import com.m3return.repository.OrdinRepository;
import com.m3return.service.OrdinService;
import com.m3return.service.dto.OrdinDTO;
import com.m3return.service.mapper.OrdinMapper;
import com.m3return.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.m3return.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrdinResource REST controller.
 *
 * @see OrdinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = M3OrderApp.class)
public class OrdinResourceIntTest {

    private static final String DEFAULT_SERIE_USER = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_USER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INCHIRIERE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INCHIRIERE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_BIKE = 1;
    private static final Integer UPDATED_ID_BIKE = 2;

    @Autowired
    private OrdinRepository ordinRepository;

    @Autowired
    private OrdinMapper ordinMapper;

    @Autowired
    private OrdinService ordinService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdinMockMvc;

    private Ordin ordin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdinResource ordinResource = new OrdinResource(ordinService);
        this.restOrdinMockMvc = MockMvcBuilders.standaloneSetup(ordinResource)
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
    public static Ordin createEntity(EntityManager em) {
        Ordin ordin = new Ordin()
            .serie_user(DEFAULT_SERIE_USER)
            .data_inchiriere(DEFAULT_DATA_INCHIRIERE)
            .id_bike(DEFAULT_ID_BIKE);
        return ordin;
    }

    @Before
    public void initTest() {
        ordin = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdin() throws Exception {
        int databaseSizeBeforeCreate = ordinRepository.findAll().size();

        // Create the Ordin
        OrdinDTO ordinDTO = ordinMapper.toDto(ordin);
        restOrdinMockMvc.perform(post("/api/ordins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinDTO)))
            .andExpect(status().isCreated());

        // Validate the Ordin in the database
        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeCreate + 1);
        Ordin testOrdin = ordinList.get(ordinList.size() - 1);
        assertThat(testOrdin.getSerie_user()).isEqualTo(DEFAULT_SERIE_USER);
        assertThat(testOrdin.getData_inchiriere()).isEqualTo(DEFAULT_DATA_INCHIRIERE);
        assertThat(testOrdin.getId_bike()).isEqualTo(DEFAULT_ID_BIKE);
    }

    @Test
    @Transactional
    public void createOrdinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordinRepository.findAll().size();

        // Create the Ordin with an existing ID
        ordin.setId(1L);
        OrdinDTO ordinDTO = ordinMapper.toDto(ordin);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdinMockMvc.perform(post("/api/ordins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ordin in the database
        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSerie_userIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordinRepository.findAll().size();
        // set the field null
        ordin.setSerie_user(null);

        // Create the Ordin, which fails.
        OrdinDTO ordinDTO = ordinMapper.toDto(ordin);

        restOrdinMockMvc.perform(post("/api/ordins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinDTO)))
            .andExpect(status().isBadRequest());

        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkData_inchiriereIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordinRepository.findAll().size();
        // set the field null
        ordin.setData_inchiriere(null);

        // Create the Ordin, which fails.
        OrdinDTO ordinDTO = ordinMapper.toDto(ordin);

        restOrdinMockMvc.perform(post("/api/ordins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinDTO)))
            .andExpect(status().isBadRequest());

        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkId_bikeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordinRepository.findAll().size();
        // set the field null
        ordin.setId_bike(null);

        // Create the Ordin, which fails.
        OrdinDTO ordinDTO = ordinMapper.toDto(ordin);

        restOrdinMockMvc.perform(post("/api/ordins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinDTO)))
            .andExpect(status().isBadRequest());

        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdins() throws Exception {
        // Initialize the database
        ordinRepository.saveAndFlush(ordin);

        // Get all the ordinList
        restOrdinMockMvc.perform(get("/api/ordins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordin.getId().intValue())))
            .andExpect(jsonPath("$.[*].serie_user").value(hasItem(DEFAULT_SERIE_USER.toString())))
            .andExpect(jsonPath("$.[*].data_inchiriere").value(hasItem(DEFAULT_DATA_INCHIRIERE.toString())))
            .andExpect(jsonPath("$.[*].id_bike").value(hasItem(DEFAULT_ID_BIKE)));
    }

    @Test
    @Transactional
    public void getOrdin() throws Exception {
        // Initialize the database
        ordinRepository.saveAndFlush(ordin);

        // Get the ordin
        restOrdinMockMvc.perform(get("/api/ordins/{id}", ordin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordin.getId().intValue()))
            .andExpect(jsonPath("$.serie_user").value(DEFAULT_SERIE_USER.toString()))
            .andExpect(jsonPath("$.data_inchiriere").value(DEFAULT_DATA_INCHIRIERE.toString()))
            .andExpect(jsonPath("$.id_bike").value(DEFAULT_ID_BIKE));
    }

    @Test
    @Transactional
    public void getNonExistingOrdin() throws Exception {
        // Get the ordin
        restOrdinMockMvc.perform(get("/api/ordins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdin() throws Exception {
        // Initialize the database
        ordinRepository.saveAndFlush(ordin);
        int databaseSizeBeforeUpdate = ordinRepository.findAll().size();

        // Update the ordin
        Ordin updatedOrdin = ordinRepository.findOne(ordin.getId());
        // Disconnect from session so that the updates on updatedOrdin are not directly saved in db
        em.detach(updatedOrdin);
        updatedOrdin
            .serie_user(UPDATED_SERIE_USER)
            .data_inchiriere(UPDATED_DATA_INCHIRIERE)
            .id_bike(UPDATED_ID_BIKE);
        OrdinDTO ordinDTO = ordinMapper.toDto(updatedOrdin);

        restOrdinMockMvc.perform(put("/api/ordins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinDTO)))
            .andExpect(status().isOk());

        // Validate the Ordin in the database
        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeUpdate);
        Ordin testOrdin = ordinList.get(ordinList.size() - 1);
        assertThat(testOrdin.getSerie_user()).isEqualTo(UPDATED_SERIE_USER);
        assertThat(testOrdin.getData_inchiriere()).isEqualTo(UPDATED_DATA_INCHIRIERE);
        assertThat(testOrdin.getId_bike()).isEqualTo(UPDATED_ID_BIKE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdin() throws Exception {
        int databaseSizeBeforeUpdate = ordinRepository.findAll().size();

        // Create the Ordin
        OrdinDTO ordinDTO = ordinMapper.toDto(ordin);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdinMockMvc.perform(put("/api/ordins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinDTO)))
            .andExpect(status().isCreated());

        // Validate the Ordin in the database
        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdin() throws Exception {
        // Initialize the database
        ordinRepository.saveAndFlush(ordin);
        int databaseSizeBeforeDelete = ordinRepository.findAll().size();

        // Get the ordin
        restOrdinMockMvc.perform(delete("/api/ordins/{id}", ordin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordin> ordinList = ordinRepository.findAll();
        assertThat(ordinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordin.class);
        Ordin ordin1 = new Ordin();
        ordin1.setId(1L);
        Ordin ordin2 = new Ordin();
        ordin2.setId(ordin1.getId());
        assertThat(ordin1).isEqualTo(ordin2);
        ordin2.setId(2L);
        assertThat(ordin1).isNotEqualTo(ordin2);
        ordin1.setId(null);
        assertThat(ordin1).isNotEqualTo(ordin2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdinDTO.class);
        OrdinDTO ordinDTO1 = new OrdinDTO();
        ordinDTO1.setId(1L);
        OrdinDTO ordinDTO2 = new OrdinDTO();
        assertThat(ordinDTO1).isNotEqualTo(ordinDTO2);
        ordinDTO2.setId(ordinDTO1.getId());
        assertThat(ordinDTO1).isEqualTo(ordinDTO2);
        ordinDTO2.setId(2L);
        assertThat(ordinDTO1).isNotEqualTo(ordinDTO2);
        ordinDTO1.setId(null);
        assertThat(ordinDTO1).isNotEqualTo(ordinDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ordinMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ordinMapper.fromId(null)).isNull();
    }
}
