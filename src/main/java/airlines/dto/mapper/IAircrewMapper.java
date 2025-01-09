package airlines.dto.mapper;

import airlines.dto.AircrewDTO;
import airlines.model.Aircrew;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAircrewMapper {
    AircrewDTO toDTO(Aircrew aircrew);
    Aircrew toEntity(AircrewDTO aircrewDTO);
}
