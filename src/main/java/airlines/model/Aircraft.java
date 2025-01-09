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
        @UniqueConstraint(columnNames = "name")
})
@Checks({
        @Check(constraints = "length_mm > 0"),
        @Check(constraints = "wingspan_mm > 0"),
        @Check(constraints = "max_speed_kmh > 0"),
        @Check(constraints = "range_km > 0"),
        @Check(constraints = "weight_kg > 0"),
        @Check(constraints = "height_m > 0"),
        @Check(constraints = "cruise_speed_kmh > 0"),
        @Check(constraints = "fuel_capacity_l > 0")
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    Manufacturer manufacturer;

    @Column(name = "weight_kg", nullable = false)
    Integer weightKg;

    @Column(name = "height_m", nullable = false)
    Integer heightM;

    @Column(name = "cruise_speed_kmh", nullable = false)
    Integer cruiseSpeedKmh;

    @Column(name = "max_fuel_capacity_l", nullable = false)
    Integer fuelCapacityL;
}
