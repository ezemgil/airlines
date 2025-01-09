package airlines.repository;

import airlines.model.FlightStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IFlightStatusRepository extends JpaRepository<FlightStatusHistory, Integer> {
    @Query(value = "SELECT * FROM flight_status_history WHERE ended_at IS NULL AND flight_id = :flightId",
            nativeQuery = true)
    FlightStatusHistory getLastActiveState(@Param("flightId") Integer flightId);
}
