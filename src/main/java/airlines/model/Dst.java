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
@Table(name = "dst", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Checks({
        @Check(constraints = "name in ('E', 'A', 'S', 'O', 'Z', 'N', 'U')")
})
public class Dst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

}
