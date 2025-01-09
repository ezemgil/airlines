package airlines.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AirplaneDTO {
    Integer id;

    AircraftDTO aircraft;

    @Pattern(regexp = "^[A-Z0-9-]+$")
    @NotBlank(message = "Registration number cannot be blank")
    String registrationNumber;

    Boolean inService;

    Date purchaseDate;

    @AssertTrue
    public boolean isPurchaseDateValid() {
        return purchaseDate != null && purchaseDate.before(new Date(System.currentTimeMillis()));
    }
}
