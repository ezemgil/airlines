package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityDTO {
    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;

    CountryDTO country;
}
