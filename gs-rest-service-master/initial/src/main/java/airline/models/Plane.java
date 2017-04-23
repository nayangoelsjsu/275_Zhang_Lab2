package airline.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.sql.DataSource;


@Entity
@Table(name = "Plane")
public class Plane {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="model")
    private String model;

    @Column(name="manufacturer")
    private String manufacturer;

    @Column(name="capacity")
    private int capacity;

    @Column(name="yearOfManufacture")
    private int yearOfManufacture;

     public Plane(String model,String manufacturer,int capacity,int yearOfManufacture) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.capacity = capacity;
        this.yearOfManufacture = yearOfManufacture;

    }
    public Plane(){
        
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

    public int getYearOfManufacture() {
        return yearOfManufacture;
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

    public void setYearOfManufacture() {
        this.yearOfManufacture = yearOfManufacture;
    }

   
}
