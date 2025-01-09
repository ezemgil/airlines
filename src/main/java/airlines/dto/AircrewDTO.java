package airlines.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AircrewDTO {

        Integer id;

        FlightDTO flightDTO;

        @NotNull(message = "Role is required")
        RoleDTO roleDTO;

        EmployeeDTO employeeDTO;
}
