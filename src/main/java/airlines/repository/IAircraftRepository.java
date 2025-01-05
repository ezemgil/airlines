package airlines.repository;

import airlines.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAircraftRepository extends JpaRepository<Aircraft, Integer> {
    boolean existsAircraftByTailNumber(String tailNumber);
}
