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
import airlines.services.interfaces.IDstOffsetService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AirportServiceImpl implements IAirportService {
    private final IAirportRepository airportRepository;
    private final IAirportMapper airportMapper;

    private final IDstOffsetService dstService;
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

    @Override
    public List<AirportDTO> getAirportsWithinRadius(AirportDTO origin, Double radius) {
        List<AirportDTO> airports = airportRepository.findAll().stream()
                .filter(airport -> !airport.getId().equals(origin.getId()))
                .filter(airport -> calculateDistance(origin.getLatitude(), origin.getLongitude(),
                        airport.getLatitude(), airport.getLongitude()) <= radius)
                .map(airportMapper::toDTO)
                .toList();

        if (airports.isEmpty()) {
            throw new AirportNotFoundException("No airports found within the given radius");
        }

        return airports;
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

    /**
     * Calculates the distance between two points on the Earth's surface using the Haversine formula.
     * The Earth is assumed to be a perfect sphere with a radius of 6378.137 kilometers.
     * @param lat1 the latitude of the first point
     * @param lon1 the longitude of the first point
     * @param lat2 the latitude of the second point
     * @param lon2 the longitude of the second point
     * @return the distance between the two points in kilometers
     */
    private static Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        double earthRadius = 6378.137;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double sinLat = Math.sin(dLat / 2);
        double sinLon = Math.sin(dLon / 2);

        double haversineFormula =
                sinLat * sinLat + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * sinLon * sinLon;
        double centralAngle = 2 * Math.sin(Math.min(1.0, Math.sqrt(haversineFormula)));

        return earthRadius * centralAngle;
    }

    public List<AirportDTO> getReachableAirports(AirportDTO origin) {
        return null;
    }
}
