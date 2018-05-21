package com.rentapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rentapp.service.BicicletaService;
import com.rentapp.web.rest.errors.BadRequestAlertException;
import com.rentapp.web.rest.util.HeaderUtil;
import com.rentapp.web.rest.util.PaginationUtil;
import com.rentapp.service.dto.BicicletaDTO;
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
 * REST controller for managing Bicicleta.
 */
@RestController
@RequestMapping("/api")
public class BicicletaResource {

    private final Logger log = LoggerFactory.getLogger(BicicletaResource.class);

    private static final String ENTITY_NAME = "bicicleta";

    private final BicicletaService bicicletaService;

    public BicicletaResource(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    /**
     * POST  /bicicletas : Create a new bicicleta.
     *
     * @param bicicletaDTO the bicicletaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bicicletaDTO, or with status 400 (Bad Request) if the bicicleta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bicicletas")
    @Timed
    public ResponseEntity<BicicletaDTO> createBicicleta(@Valid @RequestBody BicicletaDTO bicicletaDTO) throws URISyntaxException {
        log.debug("REST request to save Bicicleta : {}", bicicletaDTO);
        if (bicicletaDTO.getId() != null) {
            throw new BadRequestAlertException("A new bicicleta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BicicletaDTO result = bicicletaService.save(bicicletaDTO);
        return ResponseEntity.created(new URI("/api/bicicletas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bicicletas : Updates an existing bicicleta.
     *
     * @param bicicletaDTO the bicicletaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bicicletaDTO,
     * or with status 400 (Bad Request) if the bicicletaDTO is not valid,
     * or with status 500 (Internal Server Error) if the bicicletaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bicicletas")
    @Timed
    public ResponseEntity<BicicletaDTO> updateBicicleta(@Valid @RequestBody BicicletaDTO bicicletaDTO) throws URISyntaxException {
        log.debug("REST request to update Bicicleta : {}", bicicletaDTO);
        if (bicicletaDTO.getId() == null) {
            return createBicicleta(bicicletaDTO);
        }
        BicicletaDTO result = bicicletaService.save(bicicletaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bicicletaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bicicletas : get all the bicicletas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bicicletas in body
     */
    @GetMapping("/bicicletas")
    @Timed
    public ResponseEntity<List<BicicletaDTO>> getAllBicicletas(Pageable pageable) {
        log.debug("REST request to get a page of Bicicletas");
        Page<BicicletaDTO> page = bicicletaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bicicletas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bicicletas/:id : get the "id" bicicleta.
     *
     * @param id the id of the bicicletaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bicicletaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bicicletas/{id}")
    @Timed
    public ResponseEntity<BicicletaDTO> getBicicleta(@PathVariable Long id) {
        log.debug("REST request to get Bicicleta : {}", id);
        BicicletaDTO bicicletaDTO = bicicletaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bicicletaDTO));
    }

    /**
     * DELETE  /bicicletas/:id : delete the "id" bicicleta.
     *
     * @param id the id of the bicicletaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bicicletas/{id}")
    @Timed
    public ResponseEntity<Void> deleteBicicleta(@PathVariable Long id) {
        log.debug("REST request to delete Bicicleta : {}", id);
        bicicletaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
