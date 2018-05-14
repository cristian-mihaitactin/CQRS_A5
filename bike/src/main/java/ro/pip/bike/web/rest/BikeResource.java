package ro.pip.bike.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.pip.bike.service.BikeService;
import ro.pip.bike.web.rest.errors.BadRequestAlertException;
import ro.pip.bike.web.rest.util.HeaderUtil;
import ro.pip.bike.web.rest.util.PaginationUtil;
import ro.pip.bike.service.dto.BikeDTO;
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
 * REST controller for managing Bike.
 */
@RestController
@RequestMapping("/api")
public class BikeResource {

    private final Logger log = LoggerFactory.getLogger(BikeResource.class);

    private static final String ENTITY_NAME = "bike";

    private final BikeService bikeService;

    public BikeResource(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    /**
     * POST  /bikes : Create a new bike.
     *
     * @param bikeDTO the bikeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bikeDTO, or with status 400 (Bad Request) if the bike has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bikes")
    @Timed
    public ResponseEntity<BikeDTO> createBike(@Valid @RequestBody BikeDTO bikeDTO) throws URISyntaxException {
        log.debug("REST request to save Bike : {}", bikeDTO);
        if (bikeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bike cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BikeDTO result = bikeService.save(bikeDTO);
        return ResponseEntity.created(new URI("/api/bikes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bikes : Updates an existing bike.
     *
     * @param bikeDTO the bikeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bikeDTO,
     * or with status 400 (Bad Request) if the bikeDTO is not valid,
     * or with status 500 (Internal Server Error) if the bikeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bikes")
    @Timed
    public ResponseEntity<BikeDTO> updateBike(@Valid @RequestBody BikeDTO bikeDTO) throws URISyntaxException {
        log.debug("REST request to update Bike : {}", bikeDTO);
        if (bikeDTO.getId() == null) {
            return createBike(bikeDTO);
        }
        BikeDTO result = bikeService.save(bikeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bikeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bikes : get all the bikes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bikes in body
     */
    @GetMapping("/bikes")
    @Timed
    public ResponseEntity<List<BikeDTO>> getAllBikes(Pageable pageable) {
        log.debug("REST request to get a page of Bikes");
        Page<BikeDTO> page = bikeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bikes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bikes/:id : get the "id" bike.
     *
     * @param id the id of the bikeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bikeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bikes/{id}")
    @Timed
    public ResponseEntity<BikeDTO> getBike(@PathVariable Long id) {
        log.debug("REST request to get Bike : {}", id);
        BikeDTO bikeDTO = bikeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bikeDTO));
    }

    /**
     * DELETE  /bikes/:id : delete the "id" bike.
     *
     * @param id the id of the bikeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bikes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
        log.debug("REST request to delete Bike : {}", id);
        bikeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
