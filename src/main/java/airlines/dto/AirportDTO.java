package airlines.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AirportDTO {
    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;

    CityDTO city;

    @Size(max = 3, message = "IATA must be at most 3 characters long")
    String iata;

    @Size(max = 4, message = "ICAO must be at most 4 characters long")
    String icao;

    @NotNull(message = "Latitude is mandatory")
    @Min(value = -90, message = "Latitude must be at least -90")
    @Max(value = 90, message = "Latitude must be at most 90")
    Double latitude;

    @NotNull(message = "Longitude is mandatory")
    @Min(value = -180, message = "Longitude must be at least -180")
    @Max(value = 180, message = "Longitude must be at most 180")
    Double longitude;

    @NotNull(message = "Altitude is mandatory")
    @Min(value = -413, message = "Altitude must be at least -413")
    @Max(value = 8848, message = "Altitude must be at most 8848")
    Integer altitude;

    Double utc;

    DstDTO dst;

    String timezone;
}
