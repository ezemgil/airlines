package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryDTO {
    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;

    @Size(max = 2, message = "Code must be at most 2 characters long")
    String code;

    ContinentDTO continent;
}
