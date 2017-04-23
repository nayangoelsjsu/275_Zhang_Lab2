package dao;


import javax.transaction.Transactional;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;

import models.Flight;

@Transactional
public interface FlightDao extends CrudRepository<Flight, Long> {

  public Flight findById(String number);

}