package airlines.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightDTO {

    Integer id;

    // Automatically generated
    String flightNumber;

    @NotBlank(message = "Departure date time is mandatory")
    String departureDateTime;

    @NotBlank(message = "Scheduled date time is mandatory")
    String scheduledDateTime;

    AircraftDTO aircraft;

    AirportDTO originAirport;

    AirportDTO destinationAirport;


    @AssertTrue(message = "Departure date time must be before or equal to scheduled date time")
    private boolean isDepartureDateTimeValid() {
        return departureDateTime.compareTo(scheduledDateTime) <= 0;
    }

    @AssertTrue(message = "Origin airport must be different from destination airport")
    private boolean isOriginAirportDifferentFromDestinationAirport() {
        return !originAirport.equals(destinationAirport);
    }
}
