package airlines.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO {

    Integer id;

    @NotBlank(message = "First name is required")
    String firstName;

    @NotBlank(message = "Last name is required")
    String lastName;

    Date birthDate;

    GenderDTO genderDTO;

    CountryDTO countryDTO;

    @NotNull(message = "Hire date is required")
    Date hireDate;

    Boolean isActive;

    @AssertTrue
    public boolean isBirthDateValid() {
        return birthDate != null && birthDate.before(new Date(System.currentTimeMillis()));
    }
}
