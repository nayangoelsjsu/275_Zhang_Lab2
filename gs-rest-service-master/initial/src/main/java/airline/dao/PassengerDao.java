package dao;


import javax.transaction.Transactional;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;

import models.Passenger;

@Transactional
public interface PassengerDao extends CrudRepository<Passenger, Long> {

  public Passenger findById(int id);

}