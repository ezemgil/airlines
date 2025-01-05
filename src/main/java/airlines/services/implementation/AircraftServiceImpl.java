package airlines.services.implementation;

import airlines.dto.AircraftDTO;
import airlines.dto.mapper.IAircraftMapper;
import airlines.exceptions.AircraftNotFoundException;
import airlines.exceptions.PageNotFoundException;
import airlines.repository.IAircraftRepository;
import airlines.services.interfaces.IAircraftService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AircraftServiceImpl implements IAircraftService {
    private final IAircraftRepository aircraftRepository;
    private final IAircraftMapper aircraftMapper;

    @Override
    public Page<AircraftDTO> findAll(int page, int size, String sortBy, boolean ascending) {
        Page<AircraftDTO> aircraftPage =  aircraftRepository.findAll(
                PageRequest.of(page, size, ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()))
                .map(aircraftMapper::toDTO);
        if (aircraftPage.isEmpty()) {
            throw new PageNotFoundException("No aircraft found on the requested page");
        }
        return aircraftPage;
    }

    @Override
    public AircraftDTO findById(Integer id) {
        return aircraftMapper.toDTO(aircraftRepository.findById(id).orElseThrow(
                () -> new AircraftNotFoundException("Aircraft with id " + id + " not found")
        ));
    }
}
