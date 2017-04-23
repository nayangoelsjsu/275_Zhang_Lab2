package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;


@Entity
@Table(name = "Reservation")
public class Reservation {
    
    // @Id
    // private int id;   
@Id
    private String orderNumber;
    private Passenger passenger;
    private int price;
    private List<Flight> flights;

     public Reservation(String orderNumber,Passenger passenger,int price,List<Flight> flights) {
        this.orderNumber = orderNumber;
        this.passenger = passenger;
        this.price = price;
        this.flights = flights;

    }

//getter methods

    public String getOrderNumber() {
        return orderNumber;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public int getPrice() {
        return price;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    


//setter methods

    public void setOrderNumber() {
        this.id=id;
    }

    public void setPassenger() {
        this.firstname=firstname;
    }

    public void setPrice() {
        this.price=price;
    }

    public void setFlights() {
        this.flights=flights;
    }

   
}
