package airline.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;
import com.fasterxml.jackson.annotation.*;

import airline.models.Plane;
import java.util.*;

@Entity
@Table(name = "Flight")
public class Flight {
    
    // @Column(name= "id")
    // private int id;

    @Id
    @Column(name = "number")
    private String number;

   @Column(name = "price")
    private int price;   

    @Column(name = "source")
    private String from;

    @Column(name = "seatsLeft")
    private int seatsLeft;

    @Column(name = "destination")
    private String to;

    @Column(name = "departureTime")
    private String departureTime;

    @Column(name = "arrivalTime")
    private String arrivalTime;

    @Column(name = "description")
    private String description;

    // @Column(name = "plane_id")
    // private int plane_id;


    // @Column(name="passenger_id")
    // private String passenger_id;

    @OneToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="plane_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Plane plane;

@OneToMany
@JoinColumn(name="flight_id", referencedColumnName="number")
    private List<Passenger> passenger;

    @ManyToMany(mappedBy="flight")
    private List<Reservation> reservation;


    public Flight(List<Passenger> passenger){

        this.passenger=passenger;
        
    }

     public Flight(String number,String from,int price,String to,int seatsLeft,String departureTime,String arrivalTime,String description,Plane plane) {
    
        this.number = number;
        this.from = from;
        this.to = to;
        this.price = price;
        this.seatsLeft = seatsLeft;
        this.departureTime = departureTime;
        this.arrivalTime=arrivalTime;
        this.description=description;
        this.plane=plane;

    }

    public Flight(){
        
    }

//getter methods

    // public int getid() {
    //     return id;
    // }

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

    public String getDepartureTime() {
        return departureTime;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDescription() {
        return description;
    }
    public Plane getPlane() {
        return plane;
    }
    public List<Passenger> getPassenger() {
        return passenger;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }
//setter methods

    // public void id() {
    //     this.id=id;
    // }

   public void setNumber(String number) {
        this.number=number;
    }

    public void setFrom(String from) {
        this.from=from;
    }

    public void setTo(String to) {
        this.to=to;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft=seatsLeft;
    }

    public void setPrice(int price) {
        this.price=price;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime=departureTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime=arrivalTime;
    }

    public void setDescription(String description) {
        this.description=description;
    }
    public void setPlane(Plane plane) {
        this.plane=plane;
    }
    public void setPassenger(List<Passenger> passenger) {
        this.passenger=passenger;
    }
    public void setReservation(List<Reservation> reservation) {
        this.reservation=reservation;
    }
}
