package airlines.dto.mapper;

import airlines.dto.DstOffsetDTO;
import airlines.model.DstOffset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDstOffsetMapper {
    DstOffsetDTO toDTO(DstOffset dstOffset);
    DstOffset toEntity(DstOffsetDTO dstOffsetDTO);
}
