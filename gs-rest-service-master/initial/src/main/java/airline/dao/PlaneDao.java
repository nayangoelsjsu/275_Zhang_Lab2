package airline.dao;


import javax.transaction.Transactional;
import javax.sql.DataSource;

import org.springframework.data.repository.CrudRepository;

import airline.models.Plane;

@Transactional
public interface PlaneDao extends CrudRepository<Plane, Long> {

  public Plane findById(int id);

}