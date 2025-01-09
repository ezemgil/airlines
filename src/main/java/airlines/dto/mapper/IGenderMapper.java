package airlines.dto.mapper;

import airlines.dto.GenderDTO;
import airlines.model.Gender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IGenderMapper {
    GenderDTO toDTO(Gender gender);
    Gender toEntity(GenderDTO genderDTO);
}
