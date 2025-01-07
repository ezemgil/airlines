package airlines.services.implementation;

import airlines.dto.CityDTO;
import airlines.dto.mapper.ICityMapper;
import airlines.exceptions.notfounds.CityNotFoundException;
import airlines.repository.ICityRepository;
import airlines.services.interfaces.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityServiceImpl implements ICityService {
    private final ICityRepository cityRepository;
    private final ICityMapper cityMapper;


    @Override
    public CityDTO findById(Integer id) {
        return cityMapper.toDTO(cityRepository.findById(id).orElseThrow(
                () -> new CityNotFoundException("City with id " + id + " not found")
        ));
    }
}
