package airlines.dto.mapper;

import airlines.dto.DstDTO;
import airlines.model.Dst;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDstMapper {
    DstDTO toDTO(Dst dst);
    Dst toEntity(DstDTO dstDTO);
}
