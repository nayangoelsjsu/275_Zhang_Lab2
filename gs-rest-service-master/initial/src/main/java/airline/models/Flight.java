package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;

import java.util.*;
@Entity
@Table(name = "Flight")
public class Flight {
    
    @Id
    private int price;   

    private String number;
    private String from;
    private int seatsLeft;
    private String to;
    private Date departureTime;
    private Date arrivalTime;
    private String description;
    private Plane plane;
    private List<Passenger> passengers;

     public Flight(String number,String from,int price,String to,int seatsLeft,Date departureTime,Date arrivalTime,String description,Plane plane,List<Passenger> passengers) {
        this.number = number;
        this.from = from;
        this.to = to;
        this.price = price;
        this.seatsLeft = seatsLeft;
        this.departureTime = departureTime;
        this.arrivalTime=arrivalTime;
        this.description=description;
        this.plane=plane;
        this.passengers=passengers;

    }

//getter methods

    public String getNumber() {
        return number;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public int getPrice() {
        return price;
    }

    public Date getDepartureTime() {
        return departureTime;
    }
    public Date getArrivalTime() {
        return arrivalTime;
    }

    public String getDescription() {
        return description;
    }
    public Plane getPlane() {
        return plane;
    }
    public List<Passenger> getPassengers() {
        return passengers;
    }

//setter methods

   public void setNumber() {
        this.number=number;
    }

    public void setFrom() {
        this.from=from;
    }

    public void setTo() {
        this.to=to;
    }

    public void setSeatsLeft() {
        this.seatsLeft=seatsLeft;
    }

    public void setPrice() {
        this.price=price;
    }

    public void setDepartureTime() {
        this.departureTime=departureTime;
    }
    public void setArrivalTime() {
        this.arrivalTime=arrivalTime;
    }

    public void setDescription() {
        this.description=description;
    }
    public void setPlane() {
        this.plane=plane;
    }
    public void setPassengers() {
        this.passengers=passengers;
    }
}
