package airline.models;

import airline.models.Flight;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;


@Entity
@Table(name = "Reservation")
public class Reservation {
    
    // @Id
    // private int id;   
    @Id
    @Column(name="id")
    private int id;

    @Column(name="orderNumber")
    private String orderNumber;

    @Column(name="passenger_id")
    private String passenger_id;

    @Column(name="price")
    private int price;

    @Column(name="flight_id")
    private String flight_id;

     public Reservation(int id,String orderNumber,String passenger_id,int price,String flight_id) {
        this.id=id;
        this.orderNumber = orderNumber;
        this.passenger_id = passenger_id;
        this.price = price;
        this.flight_id = flight_id;

    }
    public Reservation(){
        
    }

//getter methods

    public int getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public int getPrice() {
        return price;
    }

    public String getFlight_id() {
        return flight_id;
    }

    


//setter methods

    public void setId(int id) {
        this.id=id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber=orderNumber;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id=passenger_id;
    }

    public void setPrice(int price) {
        this.price=price;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id=flight_id;
    }

   
}
