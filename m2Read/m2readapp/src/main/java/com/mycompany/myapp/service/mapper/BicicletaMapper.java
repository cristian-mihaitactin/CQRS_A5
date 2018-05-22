package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BicicletaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bicicleta and its DTO BicicletaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BicicletaMapper extends EntityMapper<BicicletaDTO, Bicicleta> {



    default Bicicleta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setId(id);
        return bicicleta;
    }
}
