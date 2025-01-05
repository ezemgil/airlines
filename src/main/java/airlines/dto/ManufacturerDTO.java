package airlines.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ManufacturerDTO {

    @NotNull
    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;
}
