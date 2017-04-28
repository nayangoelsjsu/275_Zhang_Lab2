package airline.dao;


import javax.transaction.Transactional;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;

import airline.models.Reservation;

@Transactional
public interface ReservationDao extends CrudRepository<Reservation, Long> {

  public Reservation findByorderNumber(String number);

  public Reservation deleteByorderNumber(String number);

  public Reservation findBypassengerId(String number);


}