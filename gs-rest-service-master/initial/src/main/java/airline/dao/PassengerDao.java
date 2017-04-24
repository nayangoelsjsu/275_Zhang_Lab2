package airline.dao;


import javax.transaction.Transactional;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;

import airline.models.Passenger;

@Transactional
public interface PassengerDao extends CrudRepository<Passenger, Integer> {

  public Passenger findById(int id);

}