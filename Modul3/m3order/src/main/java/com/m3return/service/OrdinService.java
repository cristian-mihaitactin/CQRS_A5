package com.m3return.service;

import com.m3return.service.dto.OrdinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ordin.
 */
public interface OrdinService {

    /**
     * Save a ordin.
     *
     * @param ordinDTO the entity to save
     * @return the persisted entity
     */
    OrdinDTO save(OrdinDTO ordinDTO);

    /**
     * Get all the ordins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrdinDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ordin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrdinDTO findOne(Long id);

    /**
     * Delete the "id" ordin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
