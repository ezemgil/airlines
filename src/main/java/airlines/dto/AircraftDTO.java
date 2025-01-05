package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AircraftDTO {

    @NotNull
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

    @NotBlank(message = "Tail number is mandatory")
    @Pattern(regexp = "^[A-Z]{2}-[A-Z0-9]{3,4}$", message = "Tail number must be in the format XX-XXXX")
    String tailNumber;

    @NotNull
    ManufacturerDTO manufacturer;
}
