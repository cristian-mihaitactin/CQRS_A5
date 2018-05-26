package com.ip.m1app.web.rest;

import com.ip.m1app.M1AppApp;

import com.ip.m1app.domain.M1bicicleta;
import com.ip.m1app.repository.M1bicicletaRepository;
import com.ip.m1app.service.M1bicicletaService;
import com.ip.m1app.service.dto.M1bicicletaDTO;
import com.ip.m1app.service.mapper.M1bicicletaMapper;
import com.ip.m1app.web.rest.errors.ExceptionTranslator;

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

import static com.ip.m1app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the M1bicicletaResource REST controller.
 *
 * @see M1bicicletaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = M1AppApp.class)
public class M1bicicletaResourceIntTest {

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_TIP = "AAAAAAAAAA";
    private static final String UPDATED_TIP = "BBBBBBBBBB";

    private static final Float DEFAULT_PRET = 1F;
    private static final Float UPDATED_PRET = 2F;

    private static final String DEFAULT_DESCRIERE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIERE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AN_FABRICATIE = 1950;
    private static final Integer UPDATED_AN_FABRICATIE = 1951;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private M1bicicletaRepository m1bicicletaRepository;

    @Autowired
    private M1bicicletaMapper m1bicicletaMapper;

    @Autowired
    private M1bicicletaService m1bicicletaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restM1bicicletaMockMvc;

    private M1bicicleta m1bicicleta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final M1bicicletaResource m1bicicletaResource = new M1bicicletaResource(m1bicicletaService);
        this.restM1bicicletaMockMvc = MockMvcBuilders.standaloneSetup(m1bicicletaResource)
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
    public static M1bicicleta createEntity(EntityManager em) {
        M1bicicleta m1bicicleta = new M1bicicleta()
            .serie(DEFAULT_SERIE)
            .model(DEFAULT_MODEL)
            .tip(DEFAULT_TIP)
            .pret(DEFAULT_PRET)
            .descriere(DEFAULT_DESCRIERE)
            .anFabricatie(DEFAULT_AN_FABRICATIE)
            .status(DEFAULT_STATUS);
        return m1bicicleta;
    }

    @Before
    public void initTest() {
        m1bicicleta = createEntity(em);
    }

    @Test
    @Transactional
    public void createM1bicicleta() throws Exception {
        int databaseSizeBeforeCreate = m1bicicletaRepository.findAll().size();

        // Create the M1bicicleta
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(m1bicicleta);
        restM1bicicletaMockMvc.perform(post("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isCreated());

        // Validate the M1bicicleta in the database
        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeCreate + 1);
        M1bicicleta testM1bicicleta = m1bicicletaList.get(m1bicicletaList.size() - 1);
        assertThat(testM1bicicleta.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testM1bicicleta.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testM1bicicleta.getTip()).isEqualTo(DEFAULT_TIP);
        assertThat(testM1bicicleta.getPret()).isEqualTo(DEFAULT_PRET);
        assertThat(testM1bicicleta.getDescriere()).isEqualTo(DEFAULT_DESCRIERE);
        assertThat(testM1bicicleta.getAnFabricatie()).isEqualTo(DEFAULT_AN_FABRICATIE);
        assertThat(testM1bicicleta.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createM1bicicletaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = m1bicicletaRepository.findAll().size();

        // Create the M1bicicleta with an existing ID
        m1bicicleta.setId(1L);
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(m1bicicleta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restM1bicicletaMockMvc.perform(post("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the M1bicicleta in the database
        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = m1bicicletaRepository.findAll().size();
        // set the field null
        m1bicicleta.setSerie(null);

        // Create the M1bicicleta, which fails.
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(m1bicicleta);

        restM1bicicletaMockMvc.perform(post("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = m1bicicletaRepository.findAll().size();
        // set the field null
        m1bicicleta.setModel(null);

        // Create the M1bicicleta, which fails.
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(m1bicicleta);

        restM1bicicletaMockMvc.perform(post("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPretIsRequired() throws Exception {
        int databaseSizeBeforeTest = m1bicicletaRepository.findAll().size();
        // set the field null
        m1bicicleta.setPret(null);

        // Create the M1bicicleta, which fails.
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(m1bicicleta);

        restM1bicicletaMockMvc.perform(post("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = m1bicicletaRepository.findAll().size();
        // set the field null
        m1bicicleta.setStatus(null);

        // Create the M1bicicleta, which fails.
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(m1bicicleta);

        restM1bicicletaMockMvc.perform(post("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllM1bicicletas() throws Exception {
        // Initialize the database
        m1bicicletaRepository.saveAndFlush(m1bicicleta);

        // Get all the m1bicicletaList
        restM1bicicletaMockMvc.perform(get("/api/m-1-bicicletas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m1bicicleta.getId().intValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())))
            .andExpect(jsonPath("$.[*].pret").value(hasItem(DEFAULT_PRET.doubleValue())))
            .andExpect(jsonPath("$.[*].descriere").value(hasItem(DEFAULT_DESCRIERE.toString())))
            .andExpect(jsonPath("$.[*].anFabricatie").value(hasItem(DEFAULT_AN_FABRICATIE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getM1bicicleta() throws Exception {
        // Initialize the database
        m1bicicletaRepository.saveAndFlush(m1bicicleta);

        // Get the m1bicicleta
        restM1bicicletaMockMvc.perform(get("/api/m-1-bicicletas/{id}", m1bicicleta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(m1bicicleta.getId().intValue()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()))
            .andExpect(jsonPath("$.pret").value(DEFAULT_PRET.doubleValue()))
            .andExpect(jsonPath("$.descriere").value(DEFAULT_DESCRIERE.toString()))
            .andExpect(jsonPath("$.anFabricatie").value(DEFAULT_AN_FABRICATIE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingM1bicicleta() throws Exception {
        // Get the m1bicicleta
        restM1bicicletaMockMvc.perform(get("/api/m-1-bicicletas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateM1bicicleta() throws Exception {
        // Initialize the database
        m1bicicletaRepository.saveAndFlush(m1bicicleta);
        int databaseSizeBeforeUpdate = m1bicicletaRepository.findAll().size();

        // Update the m1bicicleta
        M1bicicleta updatedM1bicicleta = m1bicicletaRepository.findOne(m1bicicleta.getId());
        // Disconnect from session so that the updates on updatedM1bicicleta are not directly saved in db
        em.detach(updatedM1bicicleta);
        updatedM1bicicleta
            .serie(UPDATED_SERIE)
            .model(UPDATED_MODEL)
            .tip(UPDATED_TIP)
            .pret(UPDATED_PRET)
            .descriere(UPDATED_DESCRIERE)
            .anFabricatie(UPDATED_AN_FABRICATIE)
            .status(UPDATED_STATUS);
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(updatedM1bicicleta);

        restM1bicicletaMockMvc.perform(put("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isOk());

        // Validate the M1bicicleta in the database
        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeUpdate);
        M1bicicleta testM1bicicleta = m1bicicletaList.get(m1bicicletaList.size() - 1);
        assertThat(testM1bicicleta.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testM1bicicleta.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testM1bicicleta.getTip()).isEqualTo(UPDATED_TIP);
        assertThat(testM1bicicleta.getPret()).isEqualTo(UPDATED_PRET);
        assertThat(testM1bicicleta.getDescriere()).isEqualTo(UPDATED_DESCRIERE);
        assertThat(testM1bicicleta.getAnFabricatie()).isEqualTo(UPDATED_AN_FABRICATIE);
        assertThat(testM1bicicleta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingM1bicicleta() throws Exception {
        int databaseSizeBeforeUpdate = m1bicicletaRepository.findAll().size();

        // Create the M1bicicleta
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaMapper.toDto(m1bicicleta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restM1bicicletaMockMvc.perform(put("/api/m-1-bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m1bicicletaDTO)))
            .andExpect(status().isCreated());

        // Validate the M1bicicleta in the database
        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteM1bicicleta() throws Exception {
        // Initialize the database
        m1bicicletaRepository.saveAndFlush(m1bicicleta);
        int databaseSizeBeforeDelete = m1bicicletaRepository.findAll().size();

        // Get the m1bicicleta
        restM1bicicletaMockMvc.perform(delete("/api/m-1-bicicletas/{id}", m1bicicleta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<M1bicicleta> m1bicicletaList = m1bicicletaRepository.findAll();
        assertThat(m1bicicletaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(M1bicicleta.class);
        M1bicicleta m1bicicleta1 = new M1bicicleta();
        m1bicicleta1.setId(1L);
        M1bicicleta m1bicicleta2 = new M1bicicleta();
        m1bicicleta2.setId(m1bicicleta1.getId());
        assertThat(m1bicicleta1).isEqualTo(m1bicicleta2);
        m1bicicleta2.setId(2L);
        assertThat(m1bicicleta1).isNotEqualTo(m1bicicleta2);
        m1bicicleta1.setId(null);
        assertThat(m1bicicleta1).isNotEqualTo(m1bicicleta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(M1bicicletaDTO.class);
        M1bicicletaDTO m1bicicletaDTO1 = new M1bicicletaDTO();
        m1bicicletaDTO1.setId(1L);
        M1bicicletaDTO m1bicicletaDTO2 = new M1bicicletaDTO();
        assertThat(m1bicicletaDTO1).isNotEqualTo(m1bicicletaDTO2);
        m1bicicletaDTO2.setId(m1bicicletaDTO1.getId());
        assertThat(m1bicicletaDTO1).isEqualTo(m1bicicletaDTO2);
        m1bicicletaDTO2.setId(2L);
        assertThat(m1bicicletaDTO1).isNotEqualTo(m1bicicletaDTO2);
        m1bicicletaDTO1.setId(null);
        assertThat(m1bicicletaDTO1).isNotEqualTo(m1bicicletaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(m1bicicletaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(m1bicicletaMapper.fromId(null)).isNull();
    }
}
