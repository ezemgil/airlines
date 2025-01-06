package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContinentDTO {
    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Code is mandatory")
    @Size(max = 3, message = "Code must be at most 3 characters long")
    String code;
}
