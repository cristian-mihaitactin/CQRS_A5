package com.ip.requestmanager.service.mapper;

import com.ip.requestmanager.domain.*;
import com.ip.requestmanager.service.dto.RequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Request and its DTO RequestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {



    default Request fromId(Long id) {
        if (id == null) {
            return null;
        }
        Request request = new Request();
        request.setId(id);
        return request;
    }
}
