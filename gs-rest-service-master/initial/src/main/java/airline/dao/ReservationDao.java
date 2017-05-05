package airline.dao;
import java.util.*;

import javax.transaction.*;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import airline.models.Reservation;

@Transactional
public interface ReservationDao extends CrudRepository<Reservation, Long> {

  public Reservation findByorderNumber(String number);

  public Reservation deleteByorderNumber(String number);

  public Reservation findBypassengerId(String number);

  @Query(value = "select order_number from lab2.reservation where passenger_id = ?", nativeQuery = true)
  List<String> findReservationByPassenger_id(String passenger_id);

  @Query(value = "select number from lab2.flight where source = ?", nativeQuery = true)
  List<String> findFlightBySource(String source);

  @Query(value = "select number from lab2.flight where destination = ?", nativeQuery = true)
  List<String> findFlightByDestination(String destination);

  @Query(value = "select order_number from lab2.reservation_flight_rel where number = ?", nativeQuery = true)
  List<String> findReservationByFlight(String number);


}