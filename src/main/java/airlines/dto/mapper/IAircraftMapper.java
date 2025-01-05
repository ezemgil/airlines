package airlines.dto.mapper;

import airlines.dto.AircraftDTO;
import airlines.model.Aircraft;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAircraftMapper {
    AircraftDTO toDTO(Aircraft aircraft);
    Aircraft toEntity(AircraftDTO aircraftDTO);
}
