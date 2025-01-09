package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AircraftDTO {

    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;

    @Positive(message = "Length must be positive")
    Integer lengthMm;

    @Positive(message = "Wingspan must be positive")
    Integer wingspanMm;

    @Positive(message = "Max speed must be positive")
    Integer maxSpeedKmh;

    @Positive(message = "Range must be positive")
    Integer rangeKm;

    ManufacturerDTO manufacturer;

    @Positive(message = "Weight must be positive")
    Integer weightKg;

    @Positive(message = "Height must be positive")
    Integer heightM;

    @Positive(message = "Cruise speed must be positive")
    Integer cruiseSpeedKmh;

    @Positive(message = "Fuel capacity must be positive")
    Integer fuelCapacityL;
}
