package com.ip.m1app.repository;

import com.ip.m1app.domain.M1bicicleta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the M1bicicleta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface M1bicicletaRepository extends JpaRepository<M1bicicleta, Long> {

}
