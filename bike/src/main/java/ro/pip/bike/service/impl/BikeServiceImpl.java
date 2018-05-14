package ro.pip.bike.service.impl;

import ro.pip.bike.service.BikeService;
import ro.pip.bike.domain.Bike;
import ro.pip.bike.repository.BikeRepository;
import ro.pip.bike.service.dto.BikeDTO;
import ro.pip.bike.service.mapper.BikeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bike.
 */
@Service
@Transactional
public class BikeServiceImpl implements BikeService {

    private final Logger log = LoggerFactory.getLogger(BikeServiceImpl.class);

    private final BikeRepository bikeRepository;

    private final BikeMapper bikeMapper;

    public BikeServiceImpl(BikeRepository bikeRepository, BikeMapper bikeMapper) {
        this.bikeRepository = bikeRepository;
        this.bikeMapper = bikeMapper;
    }

    /**
     * Save a bike.
     *
     * @param bikeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BikeDTO save(BikeDTO bikeDTO) {
        log.debug("Request to save Bike : {}", bikeDTO);
        Bike bike = bikeMapper.toEntity(bikeDTO);
        bike = bikeRepository.save(bike);
        return bikeMapper.toDto(bike);
    }

    /**
     * Get all the bikes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BikeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bikes");
        return bikeRepository.findAll(pageable)
            .map(bikeMapper::toDto);
    }

    /**
     * Get one bike by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BikeDTO findOne(Long id) {
        log.debug("Request to get Bike : {}", id);
        Bike bike = bikeRepository.findOne(id);
        return bikeMapper.toDto(bike);
    }

    /**
     * Delete the bike by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bike : {}", id);
        bikeRepository.delete(id);
    }
}
