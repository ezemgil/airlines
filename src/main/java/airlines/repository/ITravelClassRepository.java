package airlines.repository;

import airlines.model.TravelClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITravelClassRepository extends JpaRepository<TravelClass, Integer> {
}
