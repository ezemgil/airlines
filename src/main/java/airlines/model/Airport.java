package airlines.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airports", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"iata", "icao"})
})
@Checks({
        @Check(constraints = "latitude BETWEEN -90 AND 90"),
        @Check(constraints = "longitude BETWEEN -180 AND 180"),
})
public class Airport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", nullable = false)
    City city;

    @Column(name = "iata", nullable = false)
    String iata;

    @Column(name = "icao", nullable = false)
    String icao;

    @Column(name = "latitude", nullable = false)
    Double latitude;

    @Column(name = "longitude", nullable = false)
    Double longitude;

    @Column(name = "altitude", nullable = false)
    Integer altitude;

    @Column(name = "utc", nullable = false)
    Double utc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "dst_id", nullable = false)
    Dst dst;

    @Column(name = "timezone", nullable = false)
    String timezone;
}
