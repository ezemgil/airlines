package airlines.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor @NoArgsConstructor
@Table(name = "flight_status_history")
public class FlightStatusHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    Status status;

    @Column(name = "created_at")
    @CreationTimestamp
    Timestamp createdAt;

    @Column(name = "ended_at")
    Timestamp endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "changed_by", nullable = false)
    Employee changedBy;

    @Column(name = "remarks")
    String remarks;
}
