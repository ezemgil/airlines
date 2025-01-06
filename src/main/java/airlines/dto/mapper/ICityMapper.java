package airlines.dto.mapper;

import airlines.dto.CityDTO;
import airlines.model.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICityMapper {
    CityDTO toDTO(City city);
    City toEntity(CityDTO cityDTO);
}
