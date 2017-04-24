package airline.controllers;

import java.util.*;
import airline.models.Passenger;
import airline.dao.PassengerDao;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;


import java.util.Random;

@RequestMapping("/passenger")
@Controller
public class PassengerController {

@Autowired
  private PassengerDao passengerDao;

//get by ID


  public int randomIdgen(){

    Random rand = new Random();
    int  n = rand.nextInt(59900) + 100;
    return n;

  }

  @ResponseBody
  @RequestMapping(value="{id}",method = RequestMethod.GET)
  public Passenger getById(@PathVariable("id") int id) {
    String firstname="";
    String lastname="";
    int age=0;
    String gender="";
    String phone="";

    try {
      System.out.println(id);
      Passenger passenger = passengerDao.findById(id);
      firstname = passenger.getfirstname();
      lastname = passenger.getlastname();
      age = passenger.getage();
      gender = passenger.getgender();
      phone = passenger.getphone();
      System.out.println("im here as well with firstname="+firstname);
    }
    catch (Exception ex) {
      System.out.println("Im here maybe");
      return new Passenger(404,"Sorry, the requested passenger with id"+id+" does not exist");
    }

    System.out.println("firstname="+firstname);
    if(Strings.isNullOrEmpty(firstname)){
      return new Passenger(404,"Sorry, the requested passenger with id"+id+" does not exist");

    }
    else{
    return new Passenger(id,firstname,lastname,age,gender,phone);
  }
  }

//getById ends


// create passanger 


@RequestMapping(method = RequestMethod.POST)   
public Passenger create(@RequestParam Map<String,String> requestParams){

   String firstname=requestParams.get("firstname");
   String lastname=requestParams.get("lastname");
   String sage=requestParams.get("age");
   int age= Integer.parseInt(sage);
   String gender=requestParams.get("gender");
   String phone=requestParams.get("phone");
   int id=randomIdgen();

try{
      Passenger passenger = new Passenger(id,firstname, lastname, age, gender, phone);
      passengerDao.save(passenger);
    
      // passengerId = String.valueOf(passenger.getId());
}
  catch (Exception ex) {
      return new Passenger(404,ex.toString());

    }
      //return new Passenger(id,firstname,lastname,age,gender,phone);
    Passenger pass= passengerDao.findById(id);
    return pass;
}
  
  /**
   * DELETE /delete  --> Delete the user having the passed id.
   */

  @RequestMapping(value="{id}",method = RequestMethod.DELETE)
  @ResponseBody
  public Passenger delete(@PathVariable("id") int id) {
    try {
      Passenger passenger = passengerDao.findById(id);
      passengerDao.delete(passenger);
    }
    catch (Exception ex) {
      return new Passenger(404,"Sorry, the requested passenger with id"+id+" does not exist");
    }
      return new Passenger(200,"The requested passenger with id"+id+" deleted successfully");
  }


  @RequestMapping(value="{id}",method = RequestMethod.PUT)
  @ResponseBody
  public String updateUser(@RequestParam Map<String,String> requestParams, @PathVariable("id") int id) {
    try {

      String firstname=requestParams.get("firstname");
      String lastname=requestParams.get("lastname");
      String sage=requestParams.get("age");
      int age= Integer.parseInt(sage);
      String gender=requestParams.get("gender");
      String phone=requestParams.get("phone");

      Passenger user = passengerDao.findOne(id);
      user.setlastname(lastname);
      user.setage(age);
      user.setgender(gender);
      user.setphone(phone);
      passengerDao.save(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  }

  // Private fields

  
}