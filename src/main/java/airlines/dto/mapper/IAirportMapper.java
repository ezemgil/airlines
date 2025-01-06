package airlines.dto.mapper;

import airlines.dto.AirportDTO;
import airlines.model.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAirportMapper {
    AirportDTO toDTO(Airport airport);
    Airport toEntity(AirportDTO airportDTO);
}
