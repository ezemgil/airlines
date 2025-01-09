package airlines.repository;

import airlines.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPassengerRepository extends JpaRepository<Passenger, Integer> {
}
