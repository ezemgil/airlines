package airlines.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statuses", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Status {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id", referencedColumnName = "id")
    StatusScope scope;
}
