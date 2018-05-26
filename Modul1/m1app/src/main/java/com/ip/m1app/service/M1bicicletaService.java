package com.ip.m1app.service;

import com.ip.m1app.service.dto.M1bicicletaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing M1bicicleta.
 */
public interface M1bicicletaService {

    /**
     * Save a m1bicicleta.
     *
     * @param m1bicicletaDTO the entity to save
     * @return the persisted entity
     */
    M1bicicletaDTO save(M1bicicletaDTO m1bicicletaDTO);

    /**
     * Get all the m1bicicletas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<M1bicicletaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" m1bicicleta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    M1bicicletaDTO findOne(Long id);

    /**
     * Delete the "id" m1bicicleta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
