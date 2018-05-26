package com.m3return.service.impl;

import com.m3return.service.OrdinService;
import com.m3return.domain.Ordin;
import com.m3return.repository.OrdinRepository;
import com.m3return.service.dto.OrdinDTO;
import com.m3return.service.mapper.OrdinMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Ordin.
 */
@Service
@Transactional
public class OrdinServiceImpl implements OrdinService {

    private final Logger log = LoggerFactory.getLogger(OrdinServiceImpl.class);

    private final OrdinRepository ordinRepository;

    private final OrdinMapper ordinMapper;

    public OrdinServiceImpl(OrdinRepository ordinRepository, OrdinMapper ordinMapper) {
        this.ordinRepository = ordinRepository;
        this.ordinMapper = ordinMapper;
    }

    /**
     * Save a ordin.
     *
     * @param ordinDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrdinDTO save(OrdinDTO ordinDTO) {
        log.debug("Request to save Ordin : {}", ordinDTO);
        Ordin ordin = ordinMapper.toEntity(ordinDTO);
        ordin = ordinRepository.save(ordin);
        return ordinMapper.toDto(ordin);
    }

    /**
     * Get all the ordins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrdinDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ordins");
        return ordinRepository.findAll(pageable)
            .map(ordinMapper::toDto);
    }

    /**
     * Get one ordin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrdinDTO findOne(Long id) {
        log.debug("Request to get Ordin : {}", id);
        Ordin ordin = ordinRepository.findOne(id);
        return ordinMapper.toDto(ordin);
    }

    /**
     * Delete the ordin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordin : {}", id);
        ordinRepository.delete(id);
    }
}
