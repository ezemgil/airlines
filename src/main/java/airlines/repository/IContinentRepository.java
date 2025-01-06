package airlines.repository;

import airlines.model.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContinentRepository extends JpaRepository<Continent, Integer> {
}
