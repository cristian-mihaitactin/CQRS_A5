package com.m3return.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.m3return.service.OrdinService;
import com.m3return.web.rest.errors.BadRequestAlertException;
import com.m3return.web.rest.util.HeaderUtil;
import com.m3return.web.rest.util.PaginationUtil;
import com.m3return.service.dto.OrdinDTO;
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
 * REST controller for managing Ordin.
 */
@RestController
@RequestMapping("/api")
public class OrdinResource {

    private final Logger log = LoggerFactory.getLogger(OrdinResource.class);

    private static final String ENTITY_NAME = "ordin";

    private final OrdinService ordinService;

    public OrdinResource(OrdinService ordinService) {
        this.ordinService = ordinService;
    }

    /**
     * POST  /ordins : Create a new ordin.
     *
     * @param ordinDTO the ordinDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordinDTO, or with status 400 (Bad Request) if the ordin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordins")
    @Timed
    public ResponseEntity<OrdinDTO> createOrdin(@Valid @RequestBody OrdinDTO ordinDTO) throws URISyntaxException {
        log.debug("REST request to save Ordin : {}", ordinDTO);
        if (ordinDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdinDTO result = ordinService.save(ordinDTO);
        return ResponseEntity.created(new URI("/api/ordins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordins : Updates an existing ordin.
     *
     * @param ordinDTO the ordinDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordinDTO,
     * or with status 400 (Bad Request) if the ordinDTO is not valid,
     * or with status 500 (Internal Server Error) if the ordinDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordins")
    @Timed
    public ResponseEntity<OrdinDTO> updateOrdin(@Valid @RequestBody OrdinDTO ordinDTO) throws URISyntaxException {
        log.debug("REST request to update Ordin : {}", ordinDTO);
        if (ordinDTO.getId() == null) {
            return createOrdin(ordinDTO);
        }
        OrdinDTO result = ordinService.save(ordinDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordinDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordins : get all the ordins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ordins in body
     */
    @GetMapping("/ordins")
    @Timed
    public ResponseEntity<List<OrdinDTO>> getAllOrdins(Pageable pageable) {
        log.debug("REST request to get a page of Ordins");
        Page<OrdinDTO> page = ordinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordins/:id : get the "id" ordin.
     *
     * @param id the id of the ordinDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordinDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ordins/{id}")
    @Timed
    public ResponseEntity<OrdinDTO> getOrdin(@PathVariable Long id) {
        log.debug("REST request to get Ordin : {}", id);
        OrdinDTO ordinDTO = ordinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordinDTO));
    }

    /**
     * DELETE  /ordins/:id : delete the "id" ordin.
     *
     * @param id the id of the ordinDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordins/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdin(@PathVariable Long id) {
        log.debug("REST request to delete Ordin : {}", id);
        ordinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
