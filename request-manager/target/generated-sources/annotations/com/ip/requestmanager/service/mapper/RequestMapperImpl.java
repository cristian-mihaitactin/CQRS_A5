package com.ip.requestmanager.service.mapper;

import com.ip.requestmanager.domain.Request;
import com.ip.requestmanager.service.dto.RequestDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-05-18T14:15:27+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
@Component
public class RequestMapperImpl implements RequestMapper {

    @Override
    public Request toEntity(RequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Request request = new Request();

        request.setId( dto.getId() );
        request.setCommand( dto.getCommand() );
        request.setBody( dto.getBody() );

        return request;
    }

    @Override
    public RequestDTO toDto(Request entity) {
        if ( entity == null ) {
            return null;
        }

        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setId( entity.getId() );
        requestDTO.setCommand( entity.getCommand() );
        requestDTO.setBody( entity.getBody() );

        return requestDTO;
    }

    @Override
    public List<Request> toEntity(List<RequestDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Request> list = new ArrayList<Request>( dtoList.size() );
        for ( RequestDTO requestDTO : dtoList ) {
            list.add( toEntity( requestDTO ) );
        }

        return list;
    }

    @Override
    public List<RequestDTO> toDto(List<Request> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RequestDTO> list = new ArrayList<RequestDTO>( entityList.size() );
        for ( Request request : entityList ) {
            list.add( toDto( request ) );
        }

        return list;
    }
}
