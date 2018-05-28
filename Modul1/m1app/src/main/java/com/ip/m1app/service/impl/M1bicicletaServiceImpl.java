package com.ip.m1app.service.impl;

import com.ip.m1app.service.M1bicicletaService;
import com.ip.m1app.domain.M1bicicleta;
import com.ip.m1app.repository.M1bicicletaRepository;
import com.ip.m1app.service.dto.M1bicicletaDTO;
import com.ip.m1app.service.mapper.M1bicicletaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing M1bicicleta.
 */
@Service
@Transactional
public class M1bicicletaServiceImpl implements M1bicicletaService {

    private final Logger log = LoggerFactory.getLogger(M1bicicletaServiceImpl.class);

    private final M1bicicletaRepository m1bicicletaRepository;

    private final M1bicicletaMapper m1bicicletaMapper;

    public M1bicicletaServiceImpl(M1bicicletaRepository m1bicicletaRepository, M1bicicletaMapper m1bicicletaMapper) {
        this.m1bicicletaRepository = m1bicicletaRepository;
        this.m1bicicletaMapper = m1bicicletaMapper;
    }

    /**
     * Save a m1bicicleta.
     *
     * @param m1bicicletaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public M1bicicletaDTO save(M1bicicletaDTO m1bicicletaDTO) {
        log.debug("Request to save M1bicicleta : {}", m1bicicletaDTO);
        M1bicicleta m1bicicleta = m1bicicletaMapper.toEntity(m1bicicletaDTO);
        m1bicicleta = m1bicicletaRepository.save(m1bicicleta);
        return m1bicicletaMapper.toDto(m1bicicleta);
    }

    /**
     * Get all the m1bicicletas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<M1bicicletaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all M1bicicletas");
        return m1bicicletaRepository.findAll(pageable)
            .map(m1bicicletaMapper::toDto);
    }

    /**
     * Get one m1bicicleta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public M1bicicletaDTO findOne(Long id) {
        log.debug("Request to get M1bicicleta : {}", id);
        M1bicicleta m1bicicleta = m1bicicletaRepository.findOne(id);
        return m1bicicletaMapper.toDto(m1bicicleta);
    }

    /**
     * Delete the m1bicicleta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete M1bicicleta : {}", id);
        m1bicicletaRepository.delete(id);
    }

	@Override
	public void toRepair(Long id) {
        M1bicicleta bicicleta = m1bicicletaRepository.findOne(id);
        bicicleta.setStatus(3);
        m1bicicletaRepository.saveAndFlush(bicicleta);
	}

	@Override
	public void toAvailable(Long id) {
		M1bicicleta bicicleta = m1bicicletaRepository.findOne(id);
        bicicleta.setStatus(1);
        m1bicicletaRepository.saveAndFlush(bicicleta);
	}
}
