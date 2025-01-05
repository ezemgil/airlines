package airlines.services.interfaces;

import airlines.dto.AircraftDTO;
import org.springframework.data.domain.Page;

public interface IAircraftService {
    Page<AircraftDTO> findAll(int page, int size, String sortBy, boolean ascending);
    AircraftDTO findById(Integer id);
    AircraftDTO save(AircraftDTO aircraftDTO);
}
