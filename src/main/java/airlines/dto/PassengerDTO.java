package airlines.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassengerDTO {

        Integer id;

        @NotBlank(message = "First name is required")
        String firstName;

        @NotBlank(message = "Last name is required")
        String lastName;

        Date birthDate;

        GenderDTO genderDTO;

        CountryDTO countryDTO;

        @AssertTrue
        public boolean isBirthDateValid() {
                return birthDate != null && birthDate.before(new Date(System.currentTimeMillis()));
        }
}
