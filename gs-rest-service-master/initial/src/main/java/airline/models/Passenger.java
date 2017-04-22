package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;


@Entity
@Table(name = "Passenger")
public class Passenger {
    
    @Id
    private int id;   

    private String firstname;
    private String lastname;
    private int age;
    private String gender;
    private String phone;

     public Passenger(int id, String firstname,String lastname,int age,String gender,String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.age = age;
        this.phone = phone;

    }

//getter methods

    public int getId() {
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


//setter methods

    public void setId() {
        this.id=id;
    }

    public void setfirstname() {
        this.firstname=firstname;
    }

    public void setlastname() {
        this.lastname=lastname;
    }

    public void setage() {
        this.age=age;
    }

    public void setgender() {
        this.gender=gender;
    }

    public void setphone() {
        this.phone=phone;
    }

}
