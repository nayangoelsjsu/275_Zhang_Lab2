package controllers;

import models.Passenger;
import dao.PassengerDao;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reservation")
@Controller
public class ReservationController {

@Autowired
  private ReservationDao reservationDao;
  
  @ResponseBody
  @RequestMapping("{orderNumber}")
  public String getById(@PathVariable("orderNumber") String orderNumber) {
    String orderNumber="";
    int price;
    Passenger passenger;
    List<Flights> flights;
    

    try {
      Reservation reservation=reservationDao.findById(orderNumber);
      orderNumber = reservation.getOrderNumber();
      price = reservation.getPrice();
      passenger = reservation.getPassenger();
      flights = reservation.getFlights();
    }
    catch (Exception ex) {
      return "Reservation not found";
    }
    return "The order number is: " + orderNumber;
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
  
  // /**
  //  * GET /delete  --> Delete the user having the passed id.
  //  */

  // @RequestMapping("/delete")
  // @ResponseBody
  // public String delete(long id) {
  //   try {
  //     User user = new User(id);
  //     passengerDao.delete(user);
  //   }
  //   catch (Exception ex) {
  //     return "Error deleting the user:" + ex.toString();
  //   }
  //   return "User succesfully deleted!";
  // }
  
  
  
  // /**
  //  * GET /update  --> Update the email and the name for the user in the 
  //  * database having the passed id.
  //  */


  // @RequestMapping("/update")
  // @ResponseBody
  // public String updateUser(long id, String email, String name) {
  //   try {
  //     User user = passengerDao.findOne(id);
  //     user.setEmail(email);
  //     user.setName(name);
  //     passengerDao.save(user);
  //   }
  //   catch (Exception ex) {
  //     return "Error updating the user: " + ex.toString();
  //   }
  //   return "User succesfully updated!";
  // }

  // Private fields

  
}