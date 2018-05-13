package ro.pip.bike.service;

import ro.pip.bike.service.dto.BikeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Bike.
 */
public interface BikeService {

    /**
     * Save a bike.
     *
     * @param bikeDTO the entity to save
     * @return the persisted entity
     */
    BikeDTO save(BikeDTO bikeDTO);

    /**
     * Get all the bikes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BikeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bike.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BikeDTO findOne(Long id);

    /**
     * Delete the "id" bike.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
