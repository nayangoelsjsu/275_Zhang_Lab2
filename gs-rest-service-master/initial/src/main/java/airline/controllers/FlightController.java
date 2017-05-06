package airline.controllers;

import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
//import javax.faces.context.ExceptionHandler;
//package com.journaldev.spring.exceptions;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus; 

import airline.models.*;
import airline.models.Plane;
import airline.models.Passenger;
import airline.dao.*;
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


  @Autowired
  private ReservationDao reservationDao;


  public String randomIdgen(){

    Random rand = new Random();
    int  n1 = rand.nextInt(25)+65;
    int  n2 = rand.nextInt(25)+65;
    int  n3 = rand.nextInt(999)+100;
    char c1= (char)n1;
    char c2= (char)n2;
    int c3=  n3;
    String randomg= ""+c1+c2+c3;
    return randomg;

  }

@RequestMapping(value="{number}", 
            //params="json",
            produces=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)

public ResponseEntity<Flight> getFlight(@PathVariable String number) throws Exception{
    Flight flight = flightDao.findBynumber(number);

    if(flight==null)
    {
      throw new Exception("Sorry, the requested flight "+number+" does not exist-404");
    }

     List<Passenger> passengerList= flight.getPassenger();
    
    for(Passenger passenger : passengerList){
      passenger.setFlight(null);
      passenger.setReservation(null);
    }

    Flight printFlight=new Flight(flight.getNumber(), flight.getFrom(), flight.getPrice(),flight.getTo(),flight.getSeatsLeft(),flight.getDepartureTime(),flight.getArrivalTime(),flight.getDescription(),flight.getPlane());
          printFlight.setPassenger(passengerList);

    System.out.println("F: "+flight);
    if(flight==null)
    {
      throw new Exception("Sorry, the requested flight "+number+" does not exist-404");
    }
    return ResponseEntity.ok(printFlight);
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

@RequestMapping(value="{number}", 
            params="xml",
            produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.GET)
public ResponseEntity<Flight> getFlightXML(@PathVariable String number,@RequestParam boolean xml) throws Exception{
    Flight flight = flightDao.findBynumber(number);
 
 if(flight==null)
    {
      throw new Exception("Sorry, the requested flight "+number+" does not exist-404");
    }
       List<Passenger> passengerList= flight.getPassenger();
    
    for(Passenger passenger : passengerList){
      passenger.setFlight(null);
      passenger.setReservation(null);
    }

    Flight printFlight=new Flight(flight.getNumber(), flight.getFrom(), flight.getPrice(),flight.getTo(),flight.getSeatsLeft(),flight.getDepartureTime(),flight.getArrivalTime(),flight.getDescription(),flight.getPlane());
          printFlight.setPassenger(passengerList);


    
    return ResponseEntity.ok(printFlight);
}


@RequestMapping(value="{number}", 
            produces=MediaType.APPLICATION_XML_VALUE,method=RequestMethod.POST)
public ResponseEntity<Flight> createFlightXML(@PathVariable String number,@RequestParam Map<String,String> requestParams)throws Exception 
{
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
   Flight printFlight;
//try{

      String departureTime=requestParams.get("departureTime");
  // Date departureTime=new SimpleDateFormat("yyyy-mm-dd-hh").parse(sdepartureTime);

   String arrivalTime=requestParams.get("arrivalTime");
   //Date arrivalTime=new SimpleDateFormat("yyyy-mm-dd-hh").parse(sarrivalTime);

   String[] departureTimeString= departureTime.split("-");

   String[] arrivalTimeString= arrivalTime.split("-");

   int departhour= Integer.parseInt(departureTimeString[3]);
   int arrivalhour= Integer.parseInt(arrivalTimeString[3]);

List<String> reservationForTime= reservationDao.findReservationByFlight(number);
for(String resTime : reservationForTime){
  Reservation res= reservationDao.findByorderNumber(resTime);
  List<Flight> flightList= res.getFlight();

  for(Flight f : flightList ){

System.out.println("f num="+ f.getNumber()+ " number= "+ number );
    if(!f.getNumber().equals(number)){

    String arrivalTime2=f.getArrivalTime();
  // Date departureTime=new SimpleDateFormat("yyyy-mm-dd-hh").parse(sdepartureTime);
   String departureTime2=f.getDepartureTime();
   //Date arrivalTime=new SimpleDateFormat("yyyy-mm-dd-hh").parse(sarrivalTime);

   String[] departureTimeString2= departureTime2.split("-");

   String[] arrivalTimeString2= arrivalTime2.split("-");

   int departhour2= Integer.parseInt(departureTimeString2[3]);
   int arrivalhour2= Integer.parseInt(arrivalTimeString2[3]);

   System.out.println("arrivalhour= "+ arrivalhour+ " departhour2= "+ departhour2);

   if(arrivalhour>=departhour2){
      throw new Exception("Sorry, the flight is overlapping with a reserved flight- 400");

   }
}

  }
}
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
      if(pl_update==null){
      throw new Exception("Sorry, the requested flight "+number+" does not exist to be updated.-404");

      }
      else{
      pl_update.setModel(model);

      List<String> reservedSeats= reservationDao.findReservationByFlight(number);

      System.out.println("outside if reserved seats="+ reservedSeats.size()+" capacity= "+capacity);
    if(reservedSeats.size()>capacity){
      System.out.println("inside if");
      throw new Exception("Sorry, the capacity of the flight has to be more than the Reserved Seats. -400");
    }
      pl_update.setCapacity(capacity);
      fl.setSeatsLeft(capacity-reservedSeats.size());
      pl_update.setYearOfManufacture(yearOfManufacturer);
      pl_update.setManufacturer(manufacturer);

      flightDao.save(fl);
      planeDao.save(pl_update);

      List<Passenger> passengerList= fl.getPassenger();
    
    for(Passenger passenger : passengerList){
      passenger.setFlight(null);
      passenger.setReservation(null);
    }

    printFlight=new Flight(fl.getNumber(), fl.getFrom(), fl.getPrice(),fl.getTo(),fl.getSeatsLeft(),fl.getDepartureTime(),fl.getArrivalTime(),fl.getDescription(),fl.getPlane());
          printFlight.setPassenger(passengerList);

    }

    }

//}
  
      //return new Passenger(id,firstname,lastname,age,gender,phone);
    //return "Flight succesfully created with number = " + number;
       return ResponseEntity.ok(printFlight);

 }


@RequestMapping(value="{number}",method = RequestMethod.DELETE)
  @ResponseBody
  public void delete(@PathVariable("number") String number) throws Exception {
    //try {
      Flight flight = flightDao.findBynumber(number);

      if(flight==null){
      throw new Exception("Sorry, the requested flight "+number+" could not be deleted as the flight number is not present.-404");
      //return "";
    }
    else{                     
      List<String> reservationForFlight= flightDao.findReservationByFlight(number);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
      if(reservationForFlight.size()==0){
      flightDao.delete(flight);
      throw new Exception("The flight number "+number+" has been deleted successfully.-200");
      }
      else{
        throw new Exception("You can not delete a flight that has one or more reservation.-400");
      }
      
      //return "";
    }
    //}
  
   
  }

}