package com.ip.m1app.web.rest;

import com.ip.m1app.M1AppApp;

import com.ip.m1app.domain.Bicicleta;
import com.ip.m1app.repository.BicicletaRepository;
import com.ip.m1app.service.BicicletaService;
import com.ip.m1app.service.dto.BicicletaDTO;
import com.ip.m1app.service.mapper.BicicletaMapper;
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
 * Test class for the BicicletaResource REST controller.
 *
 * @see BicicletaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = M1AppApp.class)
public class BicicletaResourceIntTest {

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRET = 1D;
    private static final Double UPDATED_PRET = 2D;

    private static final String DEFAULT_TIP = "AAAAAAAAAA";
    private static final String UPDATED_TIP = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_AN_FABRICATIE = 1980;
    private static final Integer UPDATED_AN_FABRICATIE = 1981;

    private static final Integer DEFAULT_STATUS = 0;
    private static final Integer UPDATED_STATUS = 1;

    private static final String DEFAULT_DESCRIERE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIERE = "BBBBBBBBBB";

    @Autowired
    private BicicletaRepository bicicletaRepository;

    @Autowired
    private BicicletaMapper bicicletaMapper;

    @Autowired
    private BicicletaService bicicletaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBicicletaMockMvc;

    private Bicicleta bicicleta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BicicletaResource bicicletaResource = new BicicletaResource(bicicletaService);
        this.restBicicletaMockMvc = MockMvcBuilders.standaloneSetup(bicicletaResource)
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
    public static Bicicleta createEntity(EntityManager em) {
        Bicicleta bicicleta = new Bicicleta()
            .serie(DEFAULT_SERIE)
            .pret(DEFAULT_PRET)
            .tip(DEFAULT_TIP)
            .model(DEFAULT_MODEL)
            .anFabricatie(DEFAULT_AN_FABRICATIE)
            .status(DEFAULT_STATUS)
            .descriere(DEFAULT_DESCRIERE);
        return bicicleta;
    }

    @Before
    public void initTest() {
        bicicleta = createEntity(em);
    }

    @Test
    @Transactional
    public void createBicicleta() throws Exception {
        int databaseSizeBeforeCreate = bicicletaRepository.findAll().size();

        // Create the Bicicleta
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);
        restBicicletaMockMvc.perform(post("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isCreated());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeCreate + 1);
        Bicicleta testBicicleta = bicicletaList.get(bicicletaList.size() - 1);
        assertThat(testBicicleta.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testBicicleta.getPret()).isEqualTo(DEFAULT_PRET);
        assertThat(testBicicleta.getTip()).isEqualTo(DEFAULT_TIP);
        assertThat(testBicicleta.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testBicicleta.getAnFabricatie()).isEqualTo(DEFAULT_AN_FABRICATIE);
        assertThat(testBicicleta.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBicicleta.getDescriere()).isEqualTo(DEFAULT_DESCRIERE);
    }

    @Test
    @Transactional
    public void createBicicletaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bicicletaRepository.findAll().size();

        // Create the Bicicleta with an existing ID
        bicicleta.setId(1L);
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBicicletaMockMvc.perform(post("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = bicicletaRepository.findAll().size();
        // set the field null
        bicicleta.setSerie(null);

        // Create the Bicicleta, which fails.
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);

        restBicicletaMockMvc.perform(post("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPretIsRequired() throws Exception {
        int databaseSizeBeforeTest = bicicletaRepository.findAll().size();
        // set the field null
        bicicleta.setPret(null);

        // Create the Bicicleta, which fails.
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);

        restBicicletaMockMvc.perform(post("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = bicicletaRepository.findAll().size();
        // set the field null
        bicicleta.setTip(null);

        // Create the Bicicleta, which fails.
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);

        restBicicletaMockMvc.perform(post("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = bicicletaRepository.findAll().size();
        // set the field null
        bicicleta.setModel(null);

        // Create the Bicicleta, which fails.
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);

        restBicicletaMockMvc.perform(post("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = bicicletaRepository.findAll().size();
        // set the field null
        bicicleta.setStatus(null);

        // Create the Bicicleta, which fails.
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);

        restBicicletaMockMvc.perform(post("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isBadRequest());

        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBicicletas() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        // Get all the bicicletaList
        restBicicletaMockMvc.perform(get("/api/bicicletas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bicicleta.getId().intValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
            .andExpect(jsonPath("$.[*].pret").value(hasItem(DEFAULT_PRET.doubleValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].anFabricatie").value(hasItem(DEFAULT_AN_FABRICATIE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].descriere").value(hasItem(DEFAULT_DESCRIERE.toString())));
    }

    @Test
    @Transactional
    public void getBicicleta() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        // Get the bicicleta
        restBicicletaMockMvc.perform(get("/api/bicicletas/{id}", bicicleta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bicicleta.getId().intValue()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.pret").value(DEFAULT_PRET.doubleValue()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.anFabricatie").value(DEFAULT_AN_FABRICATIE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.descriere").value(DEFAULT_DESCRIERE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBicicleta() throws Exception {
        // Get the bicicleta
        restBicicletaMockMvc.perform(get("/api/bicicletas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBicicleta() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();

        // Update the bicicleta
        Bicicleta updatedBicicleta = bicicletaRepository.findOne(bicicleta.getId());
        // Disconnect from session so that the updates on updatedBicicleta are not directly saved in db
        em.detach(updatedBicicleta);
        updatedBicicleta
            .serie(UPDATED_SERIE)
            .pret(UPDATED_PRET)
            .tip(UPDATED_TIP)
            .model(UPDATED_MODEL)
            .anFabricatie(UPDATED_AN_FABRICATIE)
            .status(UPDATED_STATUS)
            .descriere(UPDATED_DESCRIERE);
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(updatedBicicleta);

        restBicicletaMockMvc.perform(put("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isOk());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
        Bicicleta testBicicleta = bicicletaList.get(bicicletaList.size() - 1);
        assertThat(testBicicleta.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testBicicleta.getPret()).isEqualTo(UPDATED_PRET);
        assertThat(testBicicleta.getTip()).isEqualTo(UPDATED_TIP);
        assertThat(testBicicleta.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testBicicleta.getAnFabricatie()).isEqualTo(UPDATED_AN_FABRICATIE);
        assertThat(testBicicleta.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBicicleta.getDescriere()).isEqualTo(UPDATED_DESCRIERE);
    }

    @Test
    @Transactional
    public void updateNonExistingBicicleta() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();

        // Create the Bicicleta
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(bicicleta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBicicletaMockMvc.perform(put("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isCreated());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBicicleta() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);
        int databaseSizeBeforeDelete = bicicletaRepository.findAll().size();

        // Get the bicicleta
        restBicicletaMockMvc.perform(delete("/api/bicicletas/{id}", bicicleta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bicicleta.class);
        Bicicleta bicicleta1 = new Bicicleta();
        bicicleta1.setId(1L);
        Bicicleta bicicleta2 = new Bicicleta();
        bicicleta2.setId(bicicleta1.getId());
        assertThat(bicicleta1).isEqualTo(bicicleta2);
        bicicleta2.setId(2L);
        assertThat(bicicleta1).isNotEqualTo(bicicleta2);
        bicicleta1.setId(null);
        assertThat(bicicleta1).isNotEqualTo(bicicleta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BicicletaDTO.class);
        BicicletaDTO bicicletaDTO1 = new BicicletaDTO();
        bicicletaDTO1.setId(1L);
        BicicletaDTO bicicletaDTO2 = new BicicletaDTO();
        assertThat(bicicletaDTO1).isNotEqualTo(bicicletaDTO2);
        bicicletaDTO2.setId(bicicletaDTO1.getId());
        assertThat(bicicletaDTO1).isEqualTo(bicicletaDTO2);
        bicicletaDTO2.setId(2L);
        assertThat(bicicletaDTO1).isNotEqualTo(bicicletaDTO2);
        bicicletaDTO1.setId(null);
        assertThat(bicicletaDTO1).isNotEqualTo(bicicletaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bicicletaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bicicletaMapper.fromId(null)).isNull();
    }
}
