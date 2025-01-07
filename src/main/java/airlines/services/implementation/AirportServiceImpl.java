package airlines.services.implementation;

import airlines.dto.AirportDTO;
import airlines.dto.mapper.IAirportMapper;
import airlines.exceptions.PageNotFoundException;
import airlines.exceptions.duplicates.DuplicateAirportException;
import airlines.exceptions.notfounds.AirportNotFoundException;
import airlines.model.Airport;
import airlines.repository.IAirportRepository;
import airlines.services.interfaces.IAirportService;
import airlines.services.interfaces.ICityService;
import airlines.services.interfaces.IDstService;
import jakarta.transaction.Transactional;
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

    private final IDstService dstService;
    private final ICityService cityService;


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

    @Override
    public AirportDTO findById(Integer id) {
        return airportMapper.toDTO(airportRepository.findById(id).orElseThrow(
                () -> new AirportNotFoundException("Airport with id " + id + " not found")
        ));
    }

    @Override @Transactional
    public AirportDTO save(AirportDTO airportDTO) {
        validateUniqueAirport(airportDTO.getIata(), airportDTO.getIcao());

        airportDTO.setDst(dstService.findById(airportDTO.getDst().getId()));
        airportDTO.setCity(cityService.findById(airportDTO.getCity().getId()));

        return airportMapper.toDTO(airportRepository.save(airportMapper.toEntity(airportDTO)));
    }

    @Override @Transactional
    public AirportDTO update(Integer id, AirportDTO airportDTO) {
        Airport existingAirport = airportMapper.toEntity(findById(id));
        if (!existingAirport.getIata().equals(airportDTO.getIata())
                || !existingAirport.getIcao().equals(airportDTO.getIcao())) {
            validateUniqueAirport(airportDTO.getIata(), airportDTO.getIcao());
        }

        airportDTO.setDst(dstService.findById(airportDTO.getDst().getId()));
        airportDTO.setCity(cityService.findById(airportDTO.getCity().getId()));

        airportMapper.updateEntityFromDTO(airportDTO, existingAirport);
        return airportMapper.toDTO(airportRepository.save(existingAirport));
    }

    @Override @Transactional
    public void delete(Integer id) {
        findById(id);
        airportRepository.deleteById(id);
    }

    /**
        * Validates if the airport with the given IATA or ICAO code already exists in the database.
        * @param iata the IATA code of the airport
        * @param icao the ICAO code of the airport
        * @throws DuplicateAirportException if the airport with the given IATA or ICAO code already exists
        *
     */
    private void validateUniqueAirport(String iata, String icao) {
        if (airportRepository.existsByIata(iata)) {
            throw new DuplicateAirportException("Airport with IATA " + iata);
        }

        if (airportRepository.existsByIcao(iata)) {
            throw new DuplicateAirportException("Airport with ICAO " + icao);
        }
    }
}
