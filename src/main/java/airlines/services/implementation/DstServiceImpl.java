package airlines.services.implementation;

import airlines.dto.DstDTO;
import airlines.dto.mapper.IDstMapper;
import airlines.exceptions.notfounds.DstNotFoundException;
import airlines.repository.IDstRepository;
import airlines.services.interfaces.IDstService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DstServiceImpl implements IDstService {
    private final IDstRepository dstRepository;
    private final IDstMapper dstMapper;

    @Override
    public DstDTO findById(Integer id) {
        return dstMapper.toDTO(dstRepository.findById(id).orElseThrow(
                () -> new DstNotFoundException("Dst with id " + id + " not found")
        ));
    }
}
