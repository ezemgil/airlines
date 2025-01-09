package airlines.repository;

import airlines.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Integer> {
}
