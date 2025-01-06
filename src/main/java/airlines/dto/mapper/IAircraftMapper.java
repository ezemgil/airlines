package airlines.dto.mapper;

import airlines.dto.AircraftDTO;
import airlines.model.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IAircraftMapper {
    AircraftDTO toDTO(Aircraft aircraft);
    Aircraft toEntity(AircraftDTO aircraftDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(AircraftDTO aircraftDTO, @MappingTarget Aircraft aircraft);
}
