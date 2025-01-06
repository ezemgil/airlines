package airlines.services.implementation;

import airlines.dto.AirportDTO;
import airlines.dto.mapper.IAirportMapper;
import airlines.exceptions.PageNotFoundException;
import airlines.repository.IAirportRepository;
import airlines.services.interfaces.IAirportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AirportServiceImpl implements IAirportService {
    private final IAirportRepository airportRepository;
    private final IAirportMapper airportMapper;


    @Override
    public Page<AirportDTO> findAll(int page, int size, String sortBy, boolean ascending) {
        Page<AirportDTO> airportPage =  airportRepository.findAll(
                PageRequest.of(page, size, ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()))
                .map(airportMapper::toDTO);
        if (airportPage.isEmpty()) {
            throw new PageNotFoundException("No airport found on the requested page");
        }
        return airportPage;
    }
}
