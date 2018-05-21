package com.rentapp.service;

import com.rentapp.service.dto.BicicletaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Bicicleta.
 */
public interface BicicletaService {

    /**
     * Save a bicicleta.
     *
     * @param bicicletaDTO the entity to save
     * @return the persisted entity
     */
    BicicletaDTO save(BicicletaDTO bicicletaDTO);

    /**
     * Get all the bicicletas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BicicletaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bicicleta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BicicletaDTO findOne(Long id);

    /**
     * Delete the "id" bicicleta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
