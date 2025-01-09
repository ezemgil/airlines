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
@AllArgsConstructor @NoArgsConstructor
@Table(name = "employees")
@Checks({
        @Check(constraints = "birth_date <= current_date")
})
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id")
    Gender gender;

    @Column(name = "birth_date", nullable = false)
    Date birthDate;

    @Column(name = "hire_date", nullable = false)
    Date hireDate = new Date(System.currentTimeMillis());

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    Country country;

    @Column(name = "is_active", nullable = false)
    Boolean isActive = true;
}
