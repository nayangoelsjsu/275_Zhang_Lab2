package airline.controllers;
import java.util.*;
import airline.models.Reservation;
import airline.models.Passenger;
import airline.dao.ReservationDao;
import airline.dao.PassengerDao;
import airline.dao.FlightDao;


import javax.sql.DataSource;
import airline.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

@RequestMapping("/reservation")
@Controller
public class ReservationController {

@Autowired
  private ReservationDao reservationDao;

@Autowired
  private FlightDao flightDao;

  @Autowired
  private PassengerDao passengerDao;

  public String randomIdgen(){

    Random rand = new Random();
    int  n1 = rand.nextInt(25)+65;
    int  n2 = rand.nextInt(25)+65;
    int  n3 = rand.nextInt(999)+100;
    char c1= (char)n1;
    char c2= (char)n2;
    int c3= n3;
    String randomg= ""+c1+c2+c3;
    return randomg;

  }

  
  // @ResponseBody
  // @RequestMapping("{orderNumber}")
  // public String getById(@PathVariable("orderNumber") String orderNumber) {
  //   String orderNum="";
  //   int price;
  //   String passenger;
  //   // List<Flight> flight;
    

  //   try {
  //     //int o=Integer.parseInt(orderNum);
  //     Reservation reservation=reservationDao.findByorderNumber(orderNumber);
  //     orderNum = reservation.getOrderNumber();
  //     price = reservation.getPrice();
  //     passenger = reservation.getPassenger_id();
  //     // flight = reservation.getFlights();
  //   }
  //   catch (Exception ex) {
  //     return "Reservation not found";
  //   }
  //   return "The order number is: " + orderNum;
  // }


  @RequestMapping(value="{number}", 
            produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)

public ResponseEntity<Reservation> getReservation(@PathVariable String number){
    Reservation reservation = reservationDao.findByorderNumber(number);
    return ResponseEntity.ok(reservation);
}


@RequestMapping(produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.POST)
public ResponseEntity<Reservation> createReservationXML(@RequestParam Map<String,String> requestParams){

  Reservation err_r=null;
  Reservation reservation=null;

  try{
  String passenger_id= requestParams.get("passengerId");
  Passenger passenger= passengerDao.findById(passenger_id);
  String flight_list= requestParams.get("flightLists");
  String[] flight_arr= flight_list.split(",");
  // List<String> listStrings = new ArrayList<String>()
  List<Flight> fl_list= new ArrayList<Flight>();
  String orderNumber= randomIdgen();

  Flight flight;
  int price=0;
  for(int i=0;i<flight_arr.length;i++){
    System.out.println("im here bro");
    flight = flightDao.findBynumber(flight_arr[i]);
    System.out.println("flight fetched="+flight.getNumber());
    price= price+ flight.getPrice();
    System.out.println("flight price="+price);
    fl_list.add(flight);
    System.out.println("flight added");

  }

    System.out.println("price="+price);


  reservation = new Reservation(orderNumber, passenger, price, fl_list);
  reservationDao.save(reservation);
    System.out.println("done bro");

      // return ResponseEntity.ok(reservation);

}

catch (Exception ex) {
    return ResponseEntity.ok(err_r);
    }
      
       return ResponseEntity.ok(reservation);
  }




// @RequestMapping(produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.POST)
// public ResponseEntity<Reservation> createReservationXML(@RequestParam Map<String,String> requestParams){

//   Reservation err_r=null;
//   Reservation reservation=null;

//   try{
//   String passenger_id= requestParams.get("passengerId");
//   Passenger passenger= passengerDao.findById(passenger_id);
//   String flight_list= requestParams.get("flightLists");
//   String[] flight_arr= flight_list.split(",");
//   // List<String> listStrings = new ArrayList<String>()
//   List<Flight> fl_list= new ArrayList<Flight>();
//   String orderNumber= randomIdgen();

//   Flight flight;
//   int price=0;
//   for(int i=0;i<flight_arr.length;i++){
//     System.out.println("im here bro");
//     flight = flightDao.findBynumber(flight_arr[i]);
//     System.out.println("flight fetched="+flight.getNumber());
//     price= price+ flight.getPrice();
//     System.out.println("flight price="+price);
//     fl_list.add(flight);
//     System.out.println("flight added");

//   }

//     System.out.println("price="+price);


//   reservation = new Reservation(orderNumber, passenger, price, fl_list);
//   reservationDao.save(reservation);
//     System.out.println("done bro");

//       // return ResponseEntity.ok(reservation);

// }

// catch (Exception ex) {
//     return ResponseEntity.ok(err_r);
//     }
      
//        return ResponseEntity.ok(reservation);
//   }




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


 // @RequestMapping(value="{orderNumber}",method = RequestMethod.DELETE)
 //  @ResponseBody
 //  public String delete(@PathVariable("orderNumber") String orderNumber) {
 //    try {
 //      Reservation reservation = reservationDao.findByorderNumber(orderNumber);
 //      System.out.println(reservation.getFlight_id());
 //      reservationDao.deleteByorderNumber(orderNumber);
 //    }
 //    catch (Exception ex) {
 //      return "Error deleting the reservation:" + ex.toString();
 //    }
 //    return "Reservation succesfully deleted!";
 //  }
  
  
  
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