package airlines.dto.mapper;

import airlines.dto.StatusDTO;
import airlines.model.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IStatusMapper {
    StatusDTO toDTO(Status status);
    Status toEntity(StatusDTO statusDTO);
}
