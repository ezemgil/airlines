package airlines.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor @NoArgsConstructor
@Table(name = "aircraft_seating", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aircraft_id", "seat_number", "seat_letter"})
})
public class AircraftSeating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    Aircraft aircraft;

    @Column(name = "seat_number", nullable = false)
    Integer seatNumber;

    @Column(name = "seat_letter", nullable = false)
    Character seatLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_class_id", nullable = false)
    TravelClass travelClass;
}
