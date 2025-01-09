package airlines.dto.mapper;

import airlines.dto.FlightDTO;
import airlines.model.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFlightMapper {
    FlightDTO toDTO(Flight flight);
    Flight toEntity(FlightDTO flightDTO);
}
