package com.rentapp.repository;

import com.rentapp.domain.Bicicleta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bicicleta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Long> {

}
