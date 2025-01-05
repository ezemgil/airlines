package airlines.services.interfaces;

import airlines.dto.ManufacturerDTO;

public interface IManufacturerService {
    ManufacturerDTO findById(Integer id);
}
