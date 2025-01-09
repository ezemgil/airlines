package airlines.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AircraftSeatingDTO {

    Integer id;

    AircraftDTO aircraft;

    @NotNull(message = "Seat number is required")
    @Positive(message = "Seat number must be positive")
    Integer seatNumber;

    @NotNull(message = "Seat letter is required")
    Character seatLetter;

    TravelClassDTO travelClass;
}
