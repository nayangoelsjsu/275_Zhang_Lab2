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

@RequestMapping("/passenger")
@Controller
public class PassengerController {

@Autowired
  private PassengerDao passengerDao;
  
  @ResponseBody
  @RequestMapping("{id}")
  public String getById(@PathVariable("id") int id) {
    String firstname="";
    String lastname="";

    try {
      Passenger passenger = passengerDao.findById(id);
      firstname = passenger.getfirstname();
      lastname = passenger.getlastname();
      int age = passenger.getage();
      String gender = passenger.getgender();
      String phone = passenger.getphone();
    }
    catch (Exception ex) {
      return "User not found";
    }
    return "The user name is: " + firstname+ " "+ lastname;
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