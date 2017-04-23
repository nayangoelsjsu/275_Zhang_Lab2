package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;


@Entity
//@Table(name = "Reservation")
public class Plane {
    
    // @Id

    private String model;
    private String manufacturer;
    private int capacity;
    private int yearOfManufacturer;

     public Plane(String model,String manufacturer,int capacity,int yearOfManufacturer) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.capacity = capacity;
        this.yearOfManufacturer = yearOfManufacturer;

    }

//getter methods

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getYearOfManufacturer() {
        return yearOfManufacturer;
    }

    


//setter methods

    public void setModel() {
       this.model = model;
    }

    public void setManufacturer() {
        this.manufacturer = manufacturer;
    }

    public void setCapacity() {
      this.capacity = capacity;
    }

    public void setYearOfManufacturer() {
        this.yearOfManufacturer = yearOfManufacturer;
    }

   
}
