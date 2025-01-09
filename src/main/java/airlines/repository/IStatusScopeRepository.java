package airlines.repository;

import airlines.model.StatusScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusScopeRepository extends JpaRepository<StatusScope, Integer> {
}
