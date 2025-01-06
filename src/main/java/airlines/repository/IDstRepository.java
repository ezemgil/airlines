package airlines.repository;

import airlines.model.Dst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDstRepository extends JpaRepository<Dst, Integer> {
}
