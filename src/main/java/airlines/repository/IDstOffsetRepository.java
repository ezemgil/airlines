package airlines.repository;

import airlines.model.DstOffset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDstOffsetRepository extends JpaRepository<DstOffset, Integer> {
}
