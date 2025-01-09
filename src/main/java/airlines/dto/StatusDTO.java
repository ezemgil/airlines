package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusDTO {

    Integer id;

    @NotBlank(message = "Name is required")
    String name;

    String description;

    StatusScopeDTO scope;
}
