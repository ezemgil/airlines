package airlines.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;

@Entity
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor @NoArgsConstructor
@Table(name = "aircraft", uniqueConstraints = {
        @UniqueConstraint(columnNames = "tail_number")
})
@Checks({
        @Check(constraints = "length_mm > 0"),
        @Check(constraints = "wingspan_mm > 0"),
        @Check(constraints = "max_speed_kmh > 0"),
        @Check(constraints = "range_km > 0"),
        @Check(constraints = "tail_number ~ '^[A-Z]{2}-[A-Z0-9]{3,4}$'")
})
public class Aircraft {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "length_mm")

    Integer lengthMm;

    @Column(name = "wingspan_mm")
    Integer wingspanMm;

    @Column(name = "max_speed_kmh")
    Integer maxSpeedKmh;

    @Column(name = "range_km")
    Integer rangeKm;

    @Column(name = "tail_number")
    String tailNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    Manufacturer manufacturer;
}
