package airlines.services.interfaces;

import airlines.dto.DstOffsetDTO;

public interface IDstOffsetService {
    DstOffsetDTO findById(Integer id);
}
