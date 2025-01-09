package airlines.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor @NoArgsConstructor
@Table(name = "flights", uniqueConstraints = {
        @UniqueConstraint(columnNames = "flight_number")
})
@Checks({
        @Check(constraints = "departure_date_time <= scheduled_date_time"),
        @Check(constraints = "origin != destination")
})
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "flight_number", nullable = false)
    String flightNumber;

    @Column(name = "departure_date_time", nullable = false)
    LocalDateTime departureDateTime;

    @Column(name = "scheduled_date_time", nullable = false)
    LocalDateTime scheduledDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_id", nullable = false)
    Aircraft aircraft;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "origin", nullable = false)
    Airport originAirport;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "destination", nullable = false)
    Airport destinationAirport;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<FlightStatusHistory> flightStatusHistories;
}
