package airlines.dto.mapper;

import airlines.dto.AirportDTO;
import airlines.model.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IAirportMapper {
    AirportDTO toDTO(Airport airport);
    Airport toEntity(AirportDTO airportDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(AirportDTO airportDTO, @MappingTarget Airport airport);
}
