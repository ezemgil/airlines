package airlines.services.implementation;

import airlines.dto.FlightStatusHistoryDTO;
import airlines.dto.mapper.IFlightStatusHistoryMapper;
import airlines.repository.IFlightStatusRepository;
import airlines.services.interfaces.IFlightStatusHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FlightStatusHistoryServiceImpl implements IFlightStatusHistoryService {
    private final IFlightStatusRepository flightStatusHistoryRepository;
    private final IFlightStatusHistoryMapper flightStatusHistoryMapper;

    public FlightStatusHistoryDTO getLastActiveState(Integer flightId) {
        return flightStatusHistoryMapper.toDTO(flightStatusHistoryRepository.getLastActiveState(flightId));
    }

}
