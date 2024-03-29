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

import airline.models.*;
import java.util.*;

@Entity
@Table(name = "Passenger")
public class Passenger {
    
    @Id
    @Column(name = "id")
    private String id;   

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

 @OneToMany(mappedBy="passenger")
private List<Reservation> reservation;

@ManyToMany(mappedBy="passenger")
    private List<Flight> flight;

    public Passenger(){

    }

    public Passenger(String id, String firstname,String lastname,int age,String gender,String phone, List<Flight> flight, List<Reservation> reservation) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.flight= flight;
        this.reservation=reservation;

    }

//getter methods

    public String getId() {
        return id;
    }

    public String getfirstname() {
        return firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public int getage() {
        return age;
    }

    public String getgender() {
        return gender;
    }

    public String getphone() {
        return phone;
    }

 public List<Flight> getFlight() {
        return flight;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }
    //  public int getcode() {
    //     return code;
    // }

    //  public String getmsg() {
    //     return msg;
    // }


//setter methods

    public void setId(String id) {
        this.id=id;
    }

    public void setfirstname(String firstname) {
        this.firstname=firstname;
    }

    public void setlastname(String lastname) {
        this.lastname=lastname;
    }

    public void setage(int age) {
        this.age=age;
    }

    public void setgender(String gender) {
        this.gender=gender;
    }

    public void setphone(String phone) {
        this.phone=phone;
    }

public void setFlight(List<Flight> flight) {
        this.flight=flight;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation=reservation;
    }

}
