package airlines.repository;

import airlines.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAirplaneRepository extends JpaRepository<Airplane, Integer> {
}
