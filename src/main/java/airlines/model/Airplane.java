package airlines.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;

import java.sql.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airplanes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "registration_number")
})
@Checks({
        @Check(constraints = "registration_number ~ '^[A-Z0-9-]+$"),
        @Check(constraints = "purchase_date <= CURRENT_DATE")
})
public class Airplane {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "aircraft_id", nullable = false)
    Aircraft aircraft;

    @Column(name = "registration_number", nullable = false)
    String registrationNumber;

    @Column(name = "in_service")
    Boolean inService;

    @Column(name = "purchase_date")
    Date purchaseDate;
}
