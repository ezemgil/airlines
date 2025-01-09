package airlines.dto.mapper;

import airlines.dto.RoleDTO;
import airlines.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    RoleDTO toDTO(Role role);
    Role toEntity(RoleDTO roleDTO);
}
