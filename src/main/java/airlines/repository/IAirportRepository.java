package airlines.repository;

import airlines.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAirportRepository extends JpaRepository<Airport, Integer> {
}
