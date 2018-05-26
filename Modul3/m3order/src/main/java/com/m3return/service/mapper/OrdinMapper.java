package com.m3return.service.mapper;

import com.m3return.domain.*;
import com.m3return.service.dto.OrdinDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ordin and its DTO OrdinDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdinMapper extends EntityMapper<OrdinDTO, Ordin> {



    default Ordin fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ordin ordin = new Ordin();
        ordin.setId(id);
        return ordin;
    }
}
