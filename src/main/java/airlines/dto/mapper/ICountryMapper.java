package airlines.dto.mapper;

import airlines.dto.CountryDTO;
import airlines.model.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICountryMapper {
    CountryDTO toDTO(Country country);
    Country toEntity(CountryDTO countryDTO);
}
