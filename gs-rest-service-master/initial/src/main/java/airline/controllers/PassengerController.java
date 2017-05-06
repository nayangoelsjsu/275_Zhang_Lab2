package airline.controllers;

import java.util.*;
import airline.models.*;
import airline.dao.PassengerDao;
import javax.sql.DataSource;
import javax.servlet.http.*;
import java.io.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus; 


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


import java.util.Random;

//@RequestMapping("/passenger")
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
@RequestMapping(value="/passenger/{id}", 
            //params="json",
            produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)

public ResponseEntity<Passenger> getPassenger(@PathVariable String id) throws Exception{
    Passenger passenger = passengerDao.findById(id);

    if(passenger==null)
    {
throw new Exception("Sorry, the requested passenger with id "+id+" does not exist-404");
    }
    List<Reservation> reservationList= passenger.getReservation();
    
    for(Reservation reservation : reservationList){
      reservation.setPassenger(null);
      List<Flight> flightList= reservation.getFlight();
      for(Flight flight : flightList){
      flight.setPassenger(null);
    }
    }

    Passenger printPassenger=new Passenger(passenger.getId(), passenger.getfirstname(), passenger.getlastname(),passenger.getage(),passenger.getgender(),passenger.getphone(),null,reservationList);


    return ResponseEntity.ok(printPassenger);
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


@RequestMapping(value="/passenger/{id}", 
            params="xml",
            produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.GET)
public ResponseEntity<Passenger> getPassengerXML(@PathVariable String id,@RequestParam boolean xml) throws Exception{
    Passenger passenger = passengerDao.findById(id);

    if(passenger==null)
    {
throw new Exception("Sorry, the requested passenger with id "+id+" does not exist-404");
    }
    
    List<Reservation> reservationList= passenger.getReservation();
    
    for(Reservation reservation : reservationList){
      reservation.setPassenger(null);
      List<Flight> flightList= reservation.getFlight();
      for(Flight flight : flightList){
      flight.setPassenger(null);
    }
    }

    Passenger printPassenger=new Passenger(passenger.getId(), passenger.getfirstname(), passenger.getlastname(),passenger.getage(),passenger.getgender(),passenger.getphone(),null,reservationList);

    if(passenger==null)
    {
throw new Exception("Sorry, the requested passenger with id "+id+" does not exist-404");
    }
    return ResponseEntity.ok(printPassenger);
}



@RequestMapping(value="/passenger", 
            //params="json",
            produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST)
//public ResponseEntity<Passenger> getPassenger(@PathVariable String id) throws Exception{
public ResponseEntity<Passenger> create(@RequestParam Map<String,String> requestParams)throws Exception{

   String firstname=requestParams.get("firstname");
   String lastname=requestParams.get("lastname");
   String sage=requestParams.get("age");
   int age= Integer.parseInt(sage);
   String gender=requestParams.get("gender");
   String phone=requestParams.get("phone");
   int id=randomIdgen();
   String sid=Integer.toString(id);


      Passenger pass= passengerDao.findById(sid);
      if(pass==null){
      Passenger passenger = new Passenger(sid,firstname, lastname, age, gender, phone,null,null);
      passengerDao.save(passenger);
    }
    else{
    throw new Exception("Another passenger with same id "+id+" exists-400");
    }

    Passenger pass1= passengerDao.findById(sid);
    return ResponseEntity.ok(pass1);
    //return pass;
}
  
  /**
   * DELETE /delete  --> Delete the user having the passed id.
   */

  @RequestMapping(value="/passenger/{id}",method = RequestMethod.DELETE)
  @ResponseBody
  public Passenger delete(@PathVariable("id") String id) throws Exception{
   
      Passenger passenger = passengerDao.findById(id);
      if(passenger==null){
throw new Exception("Passenger with id "+id+" does not exist.-404");
      }
      else{
      passengerDao.delete(passenger);
      throw new Exception("Passenger id "+id+" deleted successfully.-200");
    }
    
    
      //return new Passenger(200,"The requested passenger with id"+id+" deleted successfully");
  }


  @RequestMapping(value="/passenger/{id}",method = RequestMethod.PUT)
  @ResponseBody
  public ResponseEntity<Passenger> updateUser(@RequestParam Map<String,String> requestParams, @PathVariable("id") String id) throws Exception {
    String firstname="";
    String lastname="";
    int age=0;;
    String gender="";
    String phone="";
    String sage="";




   

       firstname=requestParams.get("firstname");
       lastname=requestParams.get("lastname");
       sage=requestParams.get("age");
       age= Integer.parseInt(sage);
       gender=requestParams.get("gender");
       phone=requestParams.get("phone");

      Passenger user = passengerDao.findById(id);
      if(user==null){
        throw new Exception("Sorry, the requested passenger with id "+id+" cannot be updated as it is not present.-404");
      }
      user.setlastname(lastname);
      user.setage(age);
      user.setgender(gender);
      user.setphone(phone);
      user.setfirstname(firstname);
      passengerDao.save(user);

      List<Reservation> reservationList= user.getReservation();
    
    for(Reservation reservation : reservationList){
      reservation.setPassenger(null);
      List<Flight> flightList= reservation.getFlight();
      for(Flight flight : flightList){
      flight.setPassenger(null);
    }
    }
    
    Passenger printPassenger=new Passenger(user.getId(), user.getfirstname(), user.getlastname(),user.getage(),user.getgender(),user.getphone(),null,reservationList);
    return ResponseEntity.ok(printPassenger);

  }

  // Private fields

  
}