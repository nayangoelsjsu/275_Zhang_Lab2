package airline.controllers;

import java.util.*;
import java.text.*;

import airline.models.Flight;
import airline.models.Plane;
import airline.models.Passenger;
import airline.dao.FlightDao;
import airline.dao.PlaneDao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;




@RequestMapping("/flight")
@Controller
public class FlightController {

@Autowired
  private FlightDao flightDao;

  @Autowired
  private PlaneDao planeDao;


  public String randomIdgen(){

    Random rand = new Random();
    int  n1 = rand.nextInt(25)+65;
    int  n2 = rand.nextInt(25)+65;
    int  n3 = rand.nextInt(999)+100;
    char c1= (char)n1;
    char c2= (char)n2;
    char c3= (char) n3;
    String randomg= ""+c1+c2+c3;
    return randomg;

  }

  // public int seatsLeft;


  // @ResponseBody
  // @RequestMapping(value="{number}",method=RequestMethod.GET)
  // public String getById(@PathVariable("number") String number) {
  //    int price;   
  //    String num="";
  //    String from="";
  //    int seatsLeft;
  //    String to="";
  //    Date departureTime;
  //    Date arrivalTime;
  //    String description="";
  //    int plane_id;
  //    // List<Passenger> passengers;
    

  //   try {
  //     Flight flight=flightDao.findBynumber(number);
  //     num = flight.getNumber();
  //     price = flight.getPrice();
  //     // passengers = flight.getPassengers();
  //     from = flight.getFrom();
  //     seatsLeft=flight.getSeatsLeft();
  //     to=flight.getTo();
  //     departureTime=flight.getDepartureTime();
  //     arrivalTime=flight.getArrivalTime();
  //     description=flight.getDescription();
  //     plane_id=flight.getPlane();

  //   }
  //   catch (Exception ex) {
  //     return "Flight not found";
  //   }
  //   return "The flight number is: " + num;
  // }




@RequestMapping(value="{number}", 
            params="json",
            produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)

public ResponseEntity<Flight> getFlight(@PathVariable String number,@RequestParam boolean json){
    Flight flight = flightDao.findBynumber(number);
    return ResponseEntity.ok(flight);
} 

@RequestMapping(value="{number}", 
            params="xml",
            produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.GET)
public ResponseEntity<Flight> getFlightXML(@PathVariable String number,@RequestParam boolean xml){
    Flight flight = flightDao.findBynumber(number);
    return ResponseEntity.ok(flight);
}


//@RequestMapping(value="{number}",method=RequestMethod.POST)   
//public String create(@RequestParam Map<String,String> requestParams,@PathVariable String number){

    // number=requestParams.get("number");
@RequestMapping(value="{number}", 
            produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.POST)
public ResponseEntity<Flight> createFlightXML(@PathVariable String number,@RequestParam Map<String,String> requestParams){
   String from=requestParams.get("from");
   String to=requestParams.get("to");
   String sprice=requestParams.get("price");
   int price= Integer.parseInt(sprice);
   String description= requestParams.get("description");
//plane
   String model=requestParams.get("model");
   String manufacturer=requestParams.get("manufacturer");
   String scapacity=requestParams.get("capacity");
   int capacity= Integer.parseInt(scapacity);
   String syearOfManufacturer=requestParams.get("yearOfManufacture");
   int yearOfManufacturer= Integer.parseInt(syearOfManufacturer);
    // int planeid= randomIdgen();x
   //String phone=requestParams.get("phone");
   //int id=2;
   Flight err_f=flightDao.findBynumber(number);
   Flight fl;
try{

      String departureTime=requestParams.get("departureTime");
  // Date departureTime=new SimpleDateFormat("yyyy-mm-dd-hh").parse(sdepartureTime);
   String arrivalTime=requestParams.get("arrivalTime");
   //Date arrivalTime=new SimpleDateFormat("yyyy-mm-dd-hh").parse(sarrivalTime);

       fl=flightDao.findBynumber(number);
       System.out.println("fl yeh hai bhai"+fl);
      if(fl==null){
      Plane plane= new Plane(model,manufacturer,capacity,yearOfManufacturer);
      planeDao.save(plane);

      Flight flight = new Flight(number,from, price, to, capacity, departureTime,arrivalTime,description,plane);
      flightDao.save(flight);

      return ResponseEntity.ok(flight);

    }
    else{


      fl.setPrice(price);
      fl.setFrom(from);
      fl.setTo(to);
      fl.setDepartureTime(departureTime);
      fl.setArrivalTime(arrivalTime);
      fl.setDescription(description);

      Plane pl=fl.getPlane();
      int pl_id=pl.getId();

      Plane pl_update= planeDao.findById(pl_id);
      pl_update.setModel(model);
      pl_update.setCapacity(capacity);
      pl_update.setYearOfManufacture(yearOfManufacturer);
      pl_update.setManufacturer(manufacturer);

      flightDao.save(fl);
      planeDao.save(pl_update);

    }

    
      // passengerId = String.valueOf(passenger.getId());
}
  catch (Exception ex) {
    return ResponseEntity.ok(err_f);
      //return "Error creating the Flight: " + ex.toString();
    }
      //return new Passenger(id,firstname,lastname,age,gender,phone);
    //return "Flight succesfully created with number = " + number;
       return ResponseEntity.ok(fl);

}

 @RequestMapping(value="{number}",method = RequestMethod.DELETE)
  @ResponseBody
  public String delete(@PathVariable("number") String number) {
    try {
      Flight flight = flightDao.findBynumber(number);
      flightDao.delete(flight);
    }
    catch (Exception ex) {
      return "Error deleting the flight:" + ex.toString();
    }
    return "Flight succesfully deleted!";
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
  
//   // /**
//   //  * GET /delete  --> Delete the user having the passed id.
//   //  */

//   // @RequestMapping("/delete")
//   // @ResponseBody
//   // public String delete(long id) {
//   //   try {
//   //     User user = new User(id);
//   //     passengerDao.delete(user);
//   //   }
//   //   catch (Exception ex) {
//   //     return "Error deleting the user:" + ex.toString();
//   //   }
//   //   return "User succesfully deleted!";
//   // }
  
  
  
//   // /**
//   //  * GET /update  --> Update the email and the name for the user in the 
//   //  * database having the passed id.
//   //  */


//   // @RequestMapping("/update")
//   // @ResponseBody
//   // public String updateUser(long id, String email, String name) {
//   //   try {
//   //     User user = passengerDao.findOne(id);
//   //     user.setEmail(email);
//   //     user.setName(name);
//   //     passengerDao.save(user);
//   //   }
//   //   catch (Exception ex) {
//   //     return "Error updating the user: " + ex.toString();
//   //   }
//   //   return "User succesfully updated!";
//   // }

//   // Private fields

  
}