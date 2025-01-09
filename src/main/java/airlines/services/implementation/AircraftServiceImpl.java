package airlines.services.implementation;

import airlines.dto.AircraftDTO;
import airlines.dto.mapper.IAircraftMapper;
import airlines.exceptions.notfounds.AircraftNotFoundException;
import airlines.exceptions.PageNotFoundException;
import airlines.model.Aircraft;
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
        aircraftDTO.setManufacturer(manufacturerService.findById(aircraftDTO.getManufacturer().getId()));
        return aircraftMapper.toDTO(aircraftRepository.save(aircraftMapper.toEntity(aircraftDTO)));
    }

    @Override @Transactional
    public AircraftDTO update(Integer id, AircraftDTO updatedAircraft) {
        Aircraft aircraft = aircraftMapper.toEntity(findById(id));
        updatedAircraft.setManufacturer(manufacturerService.findById(updatedAircraft.getManufacturer().getId()));
        aircraftMapper.updateEntityFromDTO(updatedAircraft, aircraft);
        return aircraftMapper.toDTO(aircraftRepository.save(aircraft));
    }

    @Override @Transactional
    public void delete(Integer id) {
        findById(id);
        aircraftRepository.deleteById(id);
    }

}
