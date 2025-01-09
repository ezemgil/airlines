package airlines.dto.mapper;

import airlines.dto.AirplaneDTO;
import airlines.model.Airplane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAirplaneMapper {
    AirplaneDTO toDTO(Airplane airplane);
    Airplane toEntity(AirplaneDTO airplaneDTO);
}
