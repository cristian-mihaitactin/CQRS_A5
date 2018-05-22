package com.ip.m1app.repository;

import com.ip.m1app.domain.Bicicleta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bicicleta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Long> {

}
