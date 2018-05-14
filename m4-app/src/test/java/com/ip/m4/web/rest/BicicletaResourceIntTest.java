package com.ip.m4.web.rest;

import com.ip.m4.M4App;

import com.ip.m4.domain.Bicicleta;
import com.ip.m4.repository.BicicletaRepository;
import com.ip.m4.service.BicicletaService;
import com.ip.m4.service.dto.BicicletaDTO;
import com.ip.m4.service.mapper.BicicletaMapper;
import com.ip.m4.web.rest.errors.ExceptionTranslator;

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

import static com.ip.m4.web.rest.TestUtil.createFormattingConversionService;
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
@SpringBootTest(classes = M4App.class)
public class BicicletaResourceIntTest {

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STARE = 0;
    private static final Integer UPDATED_STARE = 1;

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
            .stare(DEFAULT_STARE);
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
        assertThat(testBicicleta.getStare()).isEqualTo(DEFAULT_STARE);
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
    public void checkStareIsRequired() throws Exception {
        int databaseSizeBeforeTest = bicicletaRepository.findAll().size();
        // set the field null
        bicicleta.setStare(null);

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
            .andExpect(jsonPath("$.[*].stare").value(hasItem(DEFAULT_STARE)));
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
            .andExpect(jsonPath("$.stare").value(DEFAULT_STARE));
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
            .stare(UPDATED_STARE);
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
        assertThat(testBicicleta.getStare()).isEqualTo(UPDATED_STARE);
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
