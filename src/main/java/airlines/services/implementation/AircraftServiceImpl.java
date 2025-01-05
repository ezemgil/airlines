package airlines.services.implementation;

import airlines.dto.AircraftDTO;
import airlines.dto.mapper.IAircraftMapper;
import airlines.exceptions.notfounds.AircraftNotFoundException;
import airlines.exceptions.PageNotFoundException;
import airlines.exceptions.duplicates.DuplicateAircraftException;
import airlines.repository.IAircraftRepository;
import airlines.services.interfaces.IAircraftService;
import airlines.services.interfaces.IManufacturerService;
import jakarta.transaction.Transactional;
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
    private final IManufacturerService manufacturerService;

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

    @Override @Transactional
    public AircraftDTO save(AircraftDTO aircraftDTO) {
        validateUniqueAircraft(aircraftDTO);
        aircraftDTO.setManufacturer(manufacturerService.findById(aircraftDTO.getManufacturer().getId()));
        return aircraftMapper.toDTO(aircraftRepository.save(aircraftMapper.toEntity(aircraftDTO)));
    }

    /**
     * Validates if the aircraft is unique by tail number
     * @param aircraftDTO the aircraft to validate
     */
    private void validateUniqueAircraft(AircraftDTO aircraftDTO) {
        if (aircraftRepository.existsAircraftByTailNumber(aircraftDTO.getTailNumber())) {
            throw new DuplicateAircraftException("Aircraft with tail number " + aircraftDTO.getTailNumber() + " already exists");
        }
    }
}
