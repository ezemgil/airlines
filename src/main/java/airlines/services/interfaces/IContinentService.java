package airlines.services.interfaces;

import airlines.dto.ContinentDTO;

public interface IContinentService {
    ContinentDTO findById(Integer id);
}
