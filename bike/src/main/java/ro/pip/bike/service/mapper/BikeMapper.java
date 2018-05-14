package ro.pip.bike.service.mapper;

import ro.pip.bike.domain.*;
import ro.pip.bike.service.dto.BikeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bike and its DTO BikeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BikeMapper extends EntityMapper<BikeDTO, Bike> {



    default Bike fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bike bike = new Bike();
        bike.setId(id);
        return bike;
    }
}
