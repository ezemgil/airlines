package airlines.repository;

import airlines.model.Aircrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAircrewRepository extends JpaRepository<Aircrew, Integer> {
}
