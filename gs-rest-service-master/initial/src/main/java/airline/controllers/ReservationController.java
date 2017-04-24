package airline.controllers;
import java.util.*;
import airline.models.Reservation;
import airline.models.Passenger;
import airline.dao.ReservationDao;
import javax.sql.DataSource;
import airline.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

@RequestMapping("/reservation")
@Controller
public class ReservationController {

@Autowired
  private ReservationDao reservationDao;
  
  @ResponseBody
  @RequestMapping("{orderNumber}")
  public String getById(@PathVariable("orderNumber") String orderNumber) {
    String orderNum="";
    int price;
    String passenger;
    // List<Flight> flight;
    

    try {
      //int o=Integer.parseInt(orderNum);
      Reservation reservation=reservationDao.findByorderNumber(orderNumber);
      orderNum = reservation.getOrderNumber();
      price = reservation.getPrice();
      passenger = reservation.getPassenger_id();
      // flight = reservation.getFlights();
    }
    catch (Exception ex) {
      return "Reservation not found";
    }
    return "The order number is: " + orderNum;
  }


  // @RequestMapping("/create",method=POST)
  // @ResponseBody
  // public String create(String firstname, String lastname, int age, String gender, String phone) {
  //   String userId = "";
  //   try {
  //     Passenger passenger = new Passenger(firstname, lastname, age, gender, phone);
  //     passengerDao.save(user);
  //     passengerId = String.valueOf(passenger.getId());
  //   }
  //   catch (Exception ex) {
  //     return "Error creating the passenger: " + ex.toString();
  //   }
  //   return "Passenger succesfully created with id = " + passengerId;
  // }
  

 // @RequestMapping(value="{id}",method = RequestMethod.DELETE)
 //  @ResponseBody
 //  public Passenger delete(@PathVariable("id") int id) {
 //    try {
 //      Passenger passenger = passengerDao.findById(id);
 //      passengerDao.delete(passenger);
 //    }
 //    catch (Exception ex) {
 //      return new Passenger(404,"Sorry, the requested passenger with id"+id+" does not exist");
 //    }
 //      return new Passenger(200,"The requested passenger with id"+id+" deleted successfully");
 //  }


 @RequestMapping(value="{orderNumber}",method = RequestMethod.DELETE)
  @ResponseBody
  public String delete(@PathVariable("orderNumber") String orderNumber) {
    try {
      Reservation reservation = reservationDao.findByorderNumber(orderNumber);
      System.out.println(reservation.getFlight_id());
      reservationDao.deleteByorderNumber(orderNumber);
    }
    catch (Exception ex) {
      return "Error deleting the reservation:" + ex.toString();
    }
    return "Reservation succesfully deleted!";
  }
  
  
  
 //  // /**
 //  //  * GET /update  --> Update the email and the name for the user in the 
 //  //  * database having the passed id.
 //  //  */


 //  // @RequestMapping("/update")
 //  // @ResponseBody
 //  // public String updateUser(long id, String email, String name) {
 //  //   try {
 //  //     User user = passengerDao.findOne(id);
 //  //     user.setEmail(email);
 //  //     user.setName(name);
 //  //     passengerDao.save(user);
 //  //   }
 //  //   catch (Exception ex) {
 //  //     return "Error updating the user: " + ex.toString();
 //  //   }
 //  //   return "User succesfully updated!";
 //  // }

 //  // Private fields

  
}