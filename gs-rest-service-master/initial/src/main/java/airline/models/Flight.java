package airline.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;

import java.util.*;

@Entity
@Table(name = "Flight")
public class Flight {
    
    @Id
    @Column(name= "id")
    private int id;

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
    private Date departureTime;

    @Column(name = "arrivalTime")
    private Date arrivalTime;

    @Column(name = "description")
    private String description;

    @Column(name = "plane_id")
    private int plane_id;

    // private List<Passenger> passengers;

     public Flight(int id, String number,String from,int price,String to,int seatsLeft,Date departureTime,Date arrivalTime,String description,int plane_id) {
        this.id=id;
        this.number = number;
        this.from = from;
        this.to = to;
        this.price = price;
        this.seatsLeft = seatsLeft;
        this.departureTime = departureTime;
        this.arrivalTime=arrivalTime;
        this.description=description;
        this.plane_id=plane_id;
        // this.passengers=passengers;

    }
    public Flight(){
        
    }

//getter methods

    public int getid() {
        return id;
    }

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
    public int getPlane() {
        return plane_id;
    }
    // public List<Passenger> getPassengers() {
    //     return passengers;
    // }

//setter methods

    public void id() {
        this.id=id;
    }

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
        this.plane_id=plane_id;
    }
    // public void setPassengers() {
    //     this.passengers=passengers;
    // }
}
