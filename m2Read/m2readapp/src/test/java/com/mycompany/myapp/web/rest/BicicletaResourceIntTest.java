package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.M2ReadappApp;

import com.mycompany.myapp.domain.Bicicleta;
import com.mycompany.myapp.repository.BicicletaRepository;
import com.mycompany.myapp.service.BicicletaService;
import com.mycompany.myapp.service.dto.BicicletaDTO;
import com.mycompany.myapp.service.mapper.BicicletaMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
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
@SpringBootTest(classes = M2ReadappApp.class)
public class BicicletaResourceIntTest {

    private static final Integer DEFAULT_PRET = 1;
    private static final Integer UPDATED_PRET = 2;

    private static final LocalDate DEFAULT_DATA_INCHIRIERE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INCHIRIERE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TIMP_INCHIRIERE = 1;
    private static final Integer UPDATED_TIMP_INCHIRIERE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_CNP_RENTER = "AAAAAAAAAA";
    private static final String UPDATED_CNP_RENTER = "BBBBBBBBBB";

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
            .pret(DEFAULT_PRET)
            .data_inchiriere(DEFAULT_DATA_INCHIRIERE)
            .timp_inchiriere(DEFAULT_TIMP_INCHIRIERE)
            .status(DEFAULT_STATUS)
            .cnp_renter(DEFAULT_CNP_RENTER);
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
        assertThat(testBicicleta.getPret()).isEqualTo(DEFAULT_PRET);
        assertThat(testBicicleta.getData_inchiriere()).isEqualTo(DEFAULT_DATA_INCHIRIERE);
        assertThat(testBicicleta.getTimp_inchiriere()).isEqualTo(DEFAULT_TIMP_INCHIRIERE);
        assertThat(testBicicleta.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBicicleta.getCnp_renter()).isEqualTo(DEFAULT_CNP_RENTER);
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
    public void getAllBicicletas() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        // Get all the bicicletaList
        restBicicletaMockMvc.perform(get("/api/bicicletas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bicicleta.getId().intValue())))
            .andExpect(jsonPath("$.[*].pret").value(hasItem(DEFAULT_PRET)))
            .andExpect(jsonPath("$.[*].data_inchiriere").value(hasItem(DEFAULT_DATA_INCHIRIERE.toString())))
            .andExpect(jsonPath("$.[*].timp_inchiriere").value(hasItem(DEFAULT_TIMP_INCHIRIERE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].cnp_renter").value(hasItem(DEFAULT_CNP_RENTER.toString())));
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
            .andExpect(jsonPath("$.pret").value(DEFAULT_PRET))
            .andExpect(jsonPath("$.data_inchiriere").value(DEFAULT_DATA_INCHIRIERE.toString()))
            .andExpect(jsonPath("$.timp_inchiriere").value(DEFAULT_TIMP_INCHIRIERE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.cnp_renter").value(DEFAULT_CNP_RENTER.toString()));
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
            .pret(UPDATED_PRET)
            .data_inchiriere(UPDATED_DATA_INCHIRIERE)
            .timp_inchiriere(UPDATED_TIMP_INCHIRIERE)
            .status(UPDATED_STATUS)
            .cnp_renter(UPDATED_CNP_RENTER);
        BicicletaDTO bicicletaDTO = bicicletaMapper.toDto(updatedBicicleta);

        restBicicletaMockMvc.perform(put("/api/bicicletas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicicletaDTO)))
            .andExpect(status().isOk());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
        Bicicleta testBicicleta = bicicletaList.get(bicicletaList.size() - 1);
        assertThat(testBicicleta.getPret()).isEqualTo(UPDATED_PRET);
        assertThat(testBicicleta.getData_inchiriere()).isEqualTo(UPDATED_DATA_INCHIRIERE);
        assertThat(testBicicleta.getTimp_inchiriere()).isEqualTo(UPDATED_TIMP_INCHIRIERE);
        assertThat(testBicicleta.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBicicleta.getCnp_renter()).isEqualTo(UPDATED_CNP_RENTER);
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
