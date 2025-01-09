package airlines.repository;

import airlines.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IFlightRepository extends JpaRepository<Flight, Integer> {
    @Query(value = "select flight_number from flights order by flight_number desc", nativeQuery = true)
    String getLastFlightNumber();
}
