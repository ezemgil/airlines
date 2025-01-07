package airlines.services.interfaces;

import airlines.dto.CountryDTO;

public interface ICountryService {
    CountryDTO findById(Integer id);
}
