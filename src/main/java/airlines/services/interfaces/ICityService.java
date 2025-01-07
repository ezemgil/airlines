package airlines.services.interfaces;

import airlines.dto.CityDTO;

public interface ICityService {
    CityDTO findById(Integer id);
}
