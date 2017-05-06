package airline.controllers;
import java.util.*;
import airline.models.Reservation;
import airline.models.Passenger;
import airline.dao.ReservationDao;
import airline.dao.PassengerDao;
import airline.dao.FlightDao;
import javax.servlet.http.*;
import java.io.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus; 


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


  @RequestMapping(value="{number}", 
            produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)

public ResponseEntity<Reservation> getReservation(@PathVariable String number) throws Exception{
    Reservation reservation = reservationDao.findByorderNumber(number);
      if(reservation==null)
    {
throw new Exception("Sorry, the requested reservation number "+number+" does not exist.-404");
    }

    Passenger passenger= reservation.getPassenger();
    passenger.setFlight(null);
    passenger.setReservation(null);

    List<Flight> flightList= reservation.getFlight();
    for(Flight flight : flightList){
      flight.setPassenger(null);
      flight.setReservation(null);
    }

    Reservation printReservation=new Reservation(reservation.getOrderNumber(), passenger, reservation.getPrice(),flightList);

    return ResponseEntity.ok(printReservation);
}

@ExceptionHandler(Exception.class)
@ResponseBody
public Map<String,String> errorResponse(Exception ex, HttpServletResponse response){
Map<String,String> errorMap = new HashMap<String,String>();
String ans=ex.getMessage();
String[] a=ans.split("-");
String msg=a[0];
String code=a[1];
errorMap.put("code",code);
errorMap.put("msg",msg);
StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
ex.printStackTrace(pw);
String stackTrace = sw.toString();
//errorMap.put("errorStackTrace", stackTrace);
response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
return errorMap;
}


@RequestMapping(produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.POST)
public ResponseEntity<Reservation> createReservationXML(@RequestParam Map<String,String> requestParams) throws Exception{

  Reservation err_r=null;
  Reservation reservation=null;
  Reservation printReservation=null;

  //try{
  String passenger_id= requestParams.get("passengerId");
  Passenger passenger= passengerDao.findById(passenger_id);
  List<Reservation> passenger_reservationList= passenger.getReservation();
  List<Flight> passenger_flightList= passenger.getFlight();

  String flight_list= requestParams.get("flightLists");
  String[] flight_arr= flight_list.split(",");
  // List<String> listStrings = new ArrayList<String>()
  List<Flight> fl_list= new ArrayList<Flight>();
  String orderNumber= randomIdgen();

  Flight flight;
  int price=0;
  int seatsLeft=0;
  for(int i=0;i<flight_arr.length;i++){
    System.out.println("im here bro");
    flight = flightDao.findBynumber(flight_arr[i]);
    List <Passenger> passenger_list= flight.getPassenger();
    passenger_list.add(passenger); 
    flight.setPassenger(passenger_list);

    System.out.println("flight fetched="+flight.getNumber());
    price= price+ flight.getPrice();
    seatsLeft= flight.getSeatsLeft() -1;
    if(seatsLeft<0){
      throw new Exception("Sorry, the capacity of the flight has exeeded. -400");
    }
    flight.setSeatsLeft(seatsLeft);
    flightDao.save(flight);
    System.out.println("flight price="+price);
    fl_list.add(flight);
    System.out.println("flight added");

  }
    // passenger_flightList.add(fl_list);
    System.out.println("price="+price);
 reservation= reservationDao.findByorderNumber(orderNumber);
      if(reservation==null){
      reservation = new Reservation(orderNumber, passenger, price, fl_list);
      // passenger_reservationList.add(reservation);
      // passengerDao.save(passenger);
      reservationDao.save(reservation);

      Passenger pass= reservation.getPassenger();
    pass.setFlight(null);
    pass.setReservation(null);

    List<Flight> flightList= reservation.getFlight();
    for(Flight f : flightList){
      f.setPassenger(null);
      f.setReservation(null);
    }

    printReservation=new Reservation(reservation.getOrderNumber(), pass, reservation.getPrice(),flightList);

    }
    else{
    throw new Exception("Another reservation with same number "+orderNumber+" exists.-400");
    }
  
  
    System.out.println("done bro");
    // Reservation res1= reservationDao.findByorderNumber(orderNumber);
    return ResponseEntity.ok(printReservation);

      // return ResponseEntity.ok(reservation);

//}

//catch (Exception ex) {
    //return ResponseEntity.ok(err_r);
    //}
      
       //return ResponseEntity.ok(reservation);
  }


@RequestMapping(value="{number}", 
            produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST)
public ResponseEntity<Reservation> updateReservation(@PathVariable String number,@RequestParam Map<String,String> requestParams) throws Exception{

  Reservation err_r=null;
  Reservation reservation=null;
  Reservation printReservation=null;



  // String passenger_id= requestParams.get("passengerId");
  // Passenger passenger= passengerDao.findById(passenger_id);
  String flightsAdded= requestParams.get("flightsAdded");
  String flightsRemoved= requestParams.get("flightsRemoved");

  String[] flightadd_arr= flightsAdded.split(",");
  String[] flightrem_arr= flightsRemoved.split(",");

  // List<String> listStrings = new ArrayList<String>()

  reservation= reservationDao.findByorderNumber(number);
  if(reservation==null){
        throw new Exception("Sorry, the requested reservation with number "+number+" cannot be updated as it is not present.-404");
      }
  List<Flight> fl_list= reservation.getFlight();

  Flight flight;

  int price= reservation.getPrice();
  // int price=0;

  for(int i=0;i<flightadd_arr.length;i++){
    System.out.println("im here bro");
    flight = flightDao.findBynumber(flightadd_arr[i]);

    System.out.println("flight fetched="+flight.getNumber());
    price= price+ flight.getPrice();

    System.out.println("flight price="+price);
    fl_list.add(flight);
    System.out.println("flight added");

  }

  for(int i=0;i<flightrem_arr.length;i++){
    System.out.println("im here bro");
    flight = flightDao.findBynumber(flightrem_arr[i]);

    System.out.println("flight fetched="+flight.getNumber());
    price= price- flight.getPrice();

    System.out.println("flight price="+price);
    fl_list.remove(flight);
    System.out.println("flight removed");

  }

    System.out.println("price="+price);

Reservation res = reservationDao.findByorderNumber(number);
      if(res==null){
        throw new Exception("Sorry, the requested reservation with number "+number+" cannot be updated as it is not present.-404");
      }
      else{
  reservation.setFlight(fl_list);
  reservationDao.save(reservation);


      Passenger pass= reservation.getPassenger();
    pass.setFlight(null);
    pass.setReservation(null);

    List<Flight> flightList= reservation.getFlight();
    for(Flight f : flightList){
      f.setPassenger(null);
      f.setReservation(null);
    }

    printReservation=new Reservation(reservation.getOrderNumber(), pass, reservation.getPrice(),flightList);


}
    System.out.println("done bro");

      // return ResponseEntity.ok(reservation);
      
       return ResponseEntity.ok(printReservation);
  }



@RequestMapping(produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.GET)
public ResponseEntity<List<Reservation>> searchReservationXML(@RequestParam Map<String,String> requestParams){


  List<Reservation> err_r=null;
  Reservation print_reservation=null;
  List<Reservation> listOfReservations= new ArrayList<Reservation>();
  List<Reservation> listOfReservationsPrint= new ArrayList<Reservation>();


  try{
    
    List<String> finalReservationList= new ArrayList<String>();

    List<String> reservationList= new ArrayList<String>();


    List<String> flightListSource= new ArrayList<String>();


    List<String> reservationListFlight= new ArrayList<String>();







 String passenger_id= requestParams.get("passengerId");
 String source= requestParams.get("from");
 String destination= requestParams.get("to");
 String flightNumber= requestParams.get("flightNumber");

if(passenger_id!=null){
  System.out.println("in here bro!");
  reservationList=reservationDao.findReservationByPassenger_id(passenger_id);
System.out.println("prnting reservationList in passenger_id"+reservationList);

  if(finalReservationList.size()>0){
  System.out.println("in here in 1 bro!");

    finalReservationList.retainAll(reservationList);
  }
  else{
          for (String reservation : reservationList) {
  System.out.println("in here in 2 bro!"+reservation);
          finalReservationList.add(reservation);
        }
  System.out.println("in here in 3 bro!");


  }
  System.out.println("in here in 4 bro!");

System.out.println("prnting finalReservationList in passenger_id"+finalReservationList);

}

if(flightNumber!=null){
  reservationList=reservationDao.findReservationByFlight(flightNumber);
  if(finalReservationList.size()>0){
    finalReservationList.retainAll(reservationList);
  }
  else{
          for (String reservation : reservationList) {
              finalReservationList.add(reservation);
        }
  }

}

if(source!=null){

  flightListSource=reservationDao.findFlightBySource(source);

  for (String fl : flightListSource) {
              reservationListFlight=reservationDao.findReservationByFlight(fl);
              for (String reservation : reservationListFlight) {
              reservationList.add(reservation);
        }
        }

  if(finalReservationList.size()>0){
    finalReservationList.retainAll(reservationList);
  }
  else{
          for (String reservation : reservationList) {
              finalReservationList.add(reservation);
        }
  }

}

if(destination!=null){

  flightListSource=reservationDao.findFlightByDestination(destination);

  for (String fl : flightListSource) {
              reservationListFlight=reservationDao.findReservationByFlight(fl);
              for (String reservation : reservationListFlight) {
              reservationList.add(reservation);
        }
        }

  if(finalReservationList.size()>0){
    finalReservationList.retainAll(reservationList);
  }
  else{
          for (String reservation : reservationList) {
              finalReservationList.add(reservation);
        }
  }

}



Reservation res=null;

  for (String reservation : finalReservationList) {
          res= reservationDao.findByorderNumber(reservation);
          listOfReservations.add(res);
        }

for( Reservation resEdit : listOfReservations){

Passenger pass= resEdit.getPassenger();
    pass.setFlight(null);
    pass.setReservation(null);

    List<Flight> flightList= resEdit.getFlight();
    for(Flight f : flightList){
      f.setPassenger(null);
      f.setReservation(null);
    }

    Reservation printReservation=new Reservation(resEdit.getOrderNumber(), pass, resEdit.getPrice(),flightList);
    listOfReservationsPrint.add(printReservation);

}









System.out.println("prnting reservations"+listOfReservations);

  //   String flightNumber= requestParams.get("flightNumber");
  // Flight flight= flightDao.findBynumber(flightNumber);
  // List<Flight> fl_list= new ArrayList<Flight>();
  // fl_list.add(flight);
 // print_reservation= new Reservation(reservation.getOrderNumber(), reservation.getPassenger(), reservation.getPrice(), fl_list);

  } 
  catch(Exception e){
    return ResponseEntity.ok(err_r);

  }

       return ResponseEntity.ok(listOfReservations);

}


 @RequestMapping(value="{orderNumber}",method = RequestMethod.DELETE)
  @ResponseBody
  public String delete(@PathVariable("orderNumber") String orderNumber) throws Exception{
    
    Reservation reservation = reservationDao.findByorderNumber(orderNumber);
      if(reservation==null){
throw new Exception("Reservation with orderNumber "+orderNumber+" does not exist.-404");
      }
      else{
      
      List<Flight> flightList=reservation.getFlight();

      int seatsLeft=0;
      for(Flight flight : flightList){
        seatsLeft=flight.getSeatsLeft()+1;
        flight.setSeatsLeft(seatsLeft);
        flightDao.save(flight);
      }
      reservationDao.delete(reservation);
      // getSeatsLeft()+1;
      throw new Exception("Reservation orderNumber "+orderNumber+" deleted successfully.-200");
    }
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