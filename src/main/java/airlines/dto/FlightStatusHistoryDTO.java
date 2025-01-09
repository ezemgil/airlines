package airlines.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightStatusHistoryDTO {

    Integer id;

    @NotNull(message = "Status is required")
    StatusDTO statusDTO;

    String statusName;

    String createdAt;

    String endedAt;

    Integer changedById;

    @NotNull(message = "Changed by is required")
    EmployeeDTO changedBy;

    String remarks;
}
