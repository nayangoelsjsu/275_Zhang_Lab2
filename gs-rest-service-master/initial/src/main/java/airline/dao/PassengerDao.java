package airline.dao;

import java.util.*;

import javax.transaction.*;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.*;

import airline.models.Passenger;


@Transactional
public interface PassengerDao extends CrudRepository<Passenger, Integer> {

 public Passenger findById(String id);


 	@Modifying
@Transactional
  @Query(value = "delete from lab2.passenger_flight_rel where id = ?", nativeQuery = true)
  public void deletePassengerFlightRel(String id);

}