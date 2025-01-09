package airlines.repository;

import airlines.model.AircraftSeating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAircraftSeatingRepository extends JpaRepository<AircraftSeating, Integer> {
}
