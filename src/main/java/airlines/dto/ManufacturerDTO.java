package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManufacturerDTO {

    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;
}
