package airlines.services.interfaces;

import airlines.dto.FlightStatusHistoryDTO;

public interface IFlightStatusHistoryService {
    FlightStatusHistoryDTO getLastActiveState(Integer flightId);
}
