package airlines.dto.mapper;

import airlines.dto.TravelClassDTO;
import airlines.model.TravelClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITravelClassMapper {
    TravelClassDTO toDTO(TravelClass travelClass);
    TravelClass toEntity(TravelClassDTO travelClassDTO);
}
