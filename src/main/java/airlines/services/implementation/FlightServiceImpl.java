package airlines.services.implementation;

import airlines.dto.FlightDTO;
import airlines.dto.FlightStatusHistoryDTO;
import airlines.dto.mapper.IFlightMapper;
import airlines.repository.IFlightRepository;
import airlines.services.interfaces.IFlightService;
import airlines.services.interfaces.IFlightStatusHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FlightServiceImpl implements IFlightService {
    private final IFlightRepository flightRepository;
    private final IFlightMapper flightMapper;

    private final IFlightStatusHistoryService flightStatusHistoryService;

    /**
     * 1: Ask for departure date and time.
     * 2: Ask for the origin airport.
     * 3: Find, for the origin airport and the given departure date and time, all the aircrafts that are not in use
     * and have no scheduled flights.
     * 4: For the given aircraft and the origin airport, find all the possible destinations.
     * 5: Ask for the destination airport.
     * 6: Calculate the arrival date and time.
     * 7: Ask for confirmation.
     * 8: Generate a new flight number.
     * 9: Save the flight.
     */
    public FlightDTO schedule(FlightDTO flightDTO) {
        if (isOriginAndDestinationValid(flightDTO)) {
            throw new IllegalArgumentException("Origin and destination airports cannot be the same");
        }

        return null;
    }

    public boolean hasScheduledFlights() {
        return false;
    }

    private FlightStatusHistoryDTO getLastActiveState(Integer flightId) {
        return flightStatusHistoryService.getLastActiveState(flightId);
    }

    /**
     * Generates a new flight number based on the most recent flight number in the database.
     * If there are no flights in the database, the method returns "AAA-0001".
     * Otherwise, the method increments the numeric part of the last flight number by 1.
     * If the numeric part reaches 9999, the method increments the alphabetical part of the last flight number by 1.
     * If the alphabetical part reaches 'Z', the method increments the preceding letter and resets the current letter to 'A'.
     * If all three letters reach 'Z', the method returns "AAA-0001".
     * @return a new flight number
     */
    private String generateFlightNumber(){
        String lastFlightNumber = flightRepository.getLastFlightNumber();
        if (lastFlightNumber == null) {
            return "AAA-0001";
        }
        String[] parts = lastFlightNumber.split("-");
        String prefix = parts[0];
        int number = Integer.parseInt(parts[1]);
        if (number == 9999) {
            char first = prefix.charAt(0);
            char second = prefix.charAt(1);
            char third = prefix.charAt(2);
            if (third == 'Z') {
                if (second == 'Z') {
                    if (first == 'Z') {
                        return "AAA-0001";
                    }
                    return (char) (first + 1) + "AA-0001";
                }
                return first + "" + (char) (second + 1) + "A-0001";
            }
            return first + "" + second + (char) (third + 1) + "-0001";
        }
        return prefix + "-" + String.format("%04d", number + 1);
    }

    private boolean isOriginAndDestinationValid(FlightDTO flightDTO) {
        return flightDTO.getOriginAirport().equals(flightDTO.getDestinationAirport());
    }
}
