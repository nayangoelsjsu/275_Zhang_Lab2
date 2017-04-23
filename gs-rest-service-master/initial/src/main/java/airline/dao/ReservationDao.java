package dao;


import javax.transaction.Transactional;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;

import models.Reservation;

@Transactional
public interface ReservationDao extends CrudRepository<Reservation, Long> {

  public Reservation findById(String id);

}