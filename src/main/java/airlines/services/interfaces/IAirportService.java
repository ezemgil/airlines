package airlines.services.interfaces;

import airlines.dto.AirportDTO;
import org.springframework.data.domain.Page;

public interface IAirportService {
    Page<AirportDTO> findAll(int page, int size, String sortBy, boolean ascending);
    AirportDTO findById(Integer id);
    AirportDTO save(AirportDTO airportDTO);
    AirportDTO update(Integer id, AirportDTO airportDTO);
    void delete(Integer id);
}
