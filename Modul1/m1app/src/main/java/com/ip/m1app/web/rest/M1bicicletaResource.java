package com.ip.m1app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ip.m1app.service.M1bicicletaService;
import com.ip.m1app.web.rest.errors.BadRequestAlertException;
import com.ip.m1app.web.rest.util.HeaderUtil;
import com.ip.m1app.web.rest.util.PaginationUtil;
import com.ip.m1app.service.dto.M1bicicletaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing M1bicicleta.
 */
@RestController
@RequestMapping("/api")
public class M1bicicletaResource {

    private final Logger log = LoggerFactory.getLogger(M1bicicletaResource.class);

    private static final String ENTITY_NAME = "m1bicicleta";

    private final M1bicicletaService m1bicicletaService;

    public M1bicicletaResource(M1bicicletaService m1bicicletaService) {
        this.m1bicicletaService = m1bicicletaService;
    }

    /**
     * POST  /m-1-bicicletas : Create a new m1bicicleta.
     *
     * @param m1bicicletaDTO the m1bicicletaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new m1bicicletaDTO, or with status 400 (Bad Request) if the m1bicicleta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-1-bicicletas")
    @Timed
    public ResponseEntity<M1bicicletaDTO> createM1bicicleta(@Valid @RequestBody M1bicicletaDTO m1bicicletaDTO) throws URISyntaxException {
        log.debug("REST request to save M1bicicleta : {}", m1bicicletaDTO);
        if (m1bicicletaDTO.getId() != null) {
            throw new BadRequestAlertException("A new m1bicicleta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        M1bicicletaDTO result = m1bicicletaService.save(m1bicicletaDTO);
        return ResponseEntity.created(new URI("/api/m-1-bicicletas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-1-bicicletas : Updates an existing m1bicicleta.
     *
     * @param m1bicicletaDTO the m1bicicletaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated m1bicicletaDTO,
     * or with status 400 (Bad Request) if the m1bicicletaDTO is not valid,
     * or with status 500 (Internal Server Error) if the m1bicicletaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-1-bicicletas")
    @Timed
    public ResponseEntity<M1bicicletaDTO> updateM1bicicleta(@Valid @RequestBody M1bicicletaDTO m1bicicletaDTO) throws URISyntaxException {
        log.debug("REST request to update M1bicicleta : {}", m1bicicletaDTO);
        if (m1bicicletaDTO.getId() == null) {
            return createM1bicicleta(m1bicicletaDTO);
        }
        M1bicicletaDTO result = m1bicicletaService.save(m1bicicletaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, m1bicicletaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-1-bicicletas : get all the m1bicicletas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of m1bicicletas in body
     */
    @GetMapping("/m-1-bicicletas")
    @Timed
    public ResponseEntity<List<M1bicicletaDTO>> getAllM1bicicletas(Pageable pageable) {
        log.debug("REST request to get a page of M1bicicletas");
        Page<M1bicicletaDTO> page = m1bicicletaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-1-bicicletas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /m-1-bicicletas/:id : get the "id" m1bicicleta.
     *
     * @param id the id of the m1bicicletaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the m1bicicletaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/m-1-bicicletas/{id}")
    @Timed
    public ResponseEntity<M1bicicletaDTO> getM1bicicleta(@PathVariable Long id) {
        log.debug("REST request to get M1bicicleta : {}", id);
        M1bicicletaDTO m1bicicletaDTO = m1bicicletaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(m1bicicletaDTO));
    }

    /**
     * DELETE  /m-1-bicicletas/:id : delete the "id" m1bicicleta.
     *
     * @param id the id of the m1bicicletaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-1-bicicletas/{id}")
    @Timed
    public ResponseEntity<Void> deleteM1bicicleta(@PathVariable Long id) {
        log.debug("REST request to delete M1bicicleta : {}", id);
        m1bicicletaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @PutMapping("/m-1-bicicletas/torepair")
    @Timed
    public ResponseEntity<M1bicicletaDTO> toRepair( @RequestBody M1bicicletaDTO m1bicicletaDTO)
            throws URISyntaxException {
        log.debug("torepair REQUEST: {}", m1bicicletaDTO);
        m1bicicletaService.toRepair(m1bicicletaDTO.getId());
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, m1bicicletaDTO.getId().toString()))
                .body(m1bicicletaDTO);
    }

    @PutMapping("/m-1-bicicletas/toavailable")
    @Timed
    public ResponseEntity<M1bicicletaDTO> toAvailable(@RequestBody M1bicicletaDTO m1bicicletaDTO)
            throws URISyntaxException {
        log.debug("toAvailable Reques : {}", m1bicicletaDTO);
        m1bicicletaService.toAvailable(m1bicicletaDTO.getId());
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, m1bicicletaDTO.getId().toString()))
                .body(m1bicicletaDTO);
    }
}
