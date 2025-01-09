package airlines.dto.mapper;

import airlines.dto.StatusScopeDTO;
import airlines.model.StatusScope;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IStatusScopeMapper {
    StatusScopeDTO toDTO(StatusScope statusScope);
    StatusScope toEntity(StatusScopeDTO statusScopeDTO);
}
