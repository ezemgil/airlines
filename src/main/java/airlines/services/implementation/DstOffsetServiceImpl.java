package airlines.services.implementation;

import airlines.dto.DstOffsetDTO;
import airlines.dto.mapper.IDstOffsetMapper;
import airlines.exceptions.notfounds.DstOffsetNotFoundException;
import airlines.repository.IDstOffsetRepository;
import airlines.services.interfaces.IDstOffsetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DstOffsetServiceImpl implements IDstOffsetService {
    private final IDstOffsetRepository dstRepository;
    private final IDstOffsetMapper dstMapper;

    @Override
    public DstOffsetDTO findById(Integer id) {
        return dstMapper.toDTO(dstRepository.findById(id).orElseThrow(
                () -> new DstOffsetNotFoundException("Dst offset with id " + id + " not found")
        ));
    }
}
