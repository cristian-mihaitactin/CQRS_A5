package com.ip.m4app.service.impl;

import com.ip.m4app.service.BicicletaService;
import com.ip.m4app.domain.Bicicleta;
import com.ip.m4app.repository.BicicletaRepository;
import com.ip.m4app.service.dto.BicicletaDTO;
import com.ip.m4app.service.mapper.BicicletaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bicicleta.
 */
@Service
@Transactional
public class BicicletaServiceImpl implements BicicletaService {

    private final Logger log = LoggerFactory.getLogger(BicicletaServiceImpl.class);

    private final BicicletaRepository bicicletaRepository;

    private final BicicletaMapper bicicletaMapper;

    public BicicletaServiceImpl(BicicletaRepository bicicletaRepository, BicicletaMapper bicicletaMapper) {
        this.bicicletaRepository = bicicletaRepository;
        this.bicicletaMapper = bicicletaMapper;
    }

    /**
     * Save a bicicleta.
     *
     * @param bicicletaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BicicletaDTO save(BicicletaDTO bicicletaDTO) {
        log.debug("Request to save Bicicleta : {}", bicicletaDTO);
        Bicicleta bicicleta = bicicletaMapper.toEntity(bicicletaDTO);
        bicicleta = bicicletaRepository.save(bicicleta);
        return bicicletaMapper.toDto(bicicleta);
    }

    /**
     * Get all the bicicletas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BicicletaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bicicletas");
        return bicicletaRepository.findAll(pageable)
            .map(bicicletaMapper::toDto);
    }

    /**
     * Get one bicicleta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BicicletaDTO findOne(Long id) {
        log.debug("Request to get Bicicleta : {}", id);
        Bicicleta bicicleta = bicicletaRepository.findOne(id);
        return bicicletaMapper.toDto(bicicleta);
    }

    /**
     * Delete the bicicleta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bicicleta : {}", id);
        bicicletaRepository.delete(id);
    }
}
