package airlines.dto.mapper;

import airlines.dto.ContinentDTO;
import airlines.model.Continent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IContinentMapper {
    ContinentDTO toDTO(Continent continent);
    Continent toEntity(ContinentDTO continentDTO);
}
