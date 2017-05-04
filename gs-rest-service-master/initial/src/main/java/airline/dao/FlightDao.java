package airline.dao;
import java.util.*;

import javax.transaction.*;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import airline.models.Flight;

@Transactional
public interface FlightDao extends CrudRepository<Flight, Long> {

  public Flight findBynumber(String number);
    
    @Query(value = "select distinct order_number from lab2.reservation_flight_rel where number = ?", nativeQuery = true)
    List<String> findReservationByFlight(String number);
}