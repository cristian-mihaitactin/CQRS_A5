package com.m3return.repository;

import com.m3return.domain.Ordin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ordin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdinRepository extends JpaRepository<Ordin, Long> {

}
