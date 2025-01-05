package airlines.dto.mapper;

import airlines.dto.ManufacturerDTO;
import airlines.model.Manufacturer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IManufacturerMapper {
    ManufacturerDTO toDTO(Manufacturer manufacturer);
    Manufacturer toEntity(ManufacturerDTO manufacturerDTO);
}
