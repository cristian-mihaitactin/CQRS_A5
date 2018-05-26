package com.ip.m1app.service.mapper;

import com.ip.m1app.domain.*;
import com.ip.m1app.service.dto.M1bicicletaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity M1bicicleta and its DTO M1bicicletaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface M1bicicletaMapper extends EntityMapper<M1bicicletaDTO, M1bicicleta> {



    default M1bicicleta fromId(Long id) {
        if (id == null) {
            return null;
        }
        M1bicicleta m1bicicleta = new M1bicicleta();
        m1bicicleta.setId(id);
        return m1bicicleta;
    }
}
