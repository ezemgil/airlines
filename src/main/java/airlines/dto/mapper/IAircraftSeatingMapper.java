package airlines.dto.mapper;

import airlines.dto.AircraftSeatingDTO;
import airlines.model.AircraftSeating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAircraftSeatingMapper {
    AircraftSeatingDTO toDTO(AircraftSeating aircraftSeating);
    AircraftSeating toEntity(AircraftSeatingDTO aircraftSeatingDTO);
}
