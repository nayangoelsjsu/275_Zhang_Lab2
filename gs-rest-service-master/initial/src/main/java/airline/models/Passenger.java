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
@Table(name = "Passenger")
public class Passenger {
    
    @Id
    @Column(name = "id")
    private int id;   

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

    private int code;
    private String msg;

    public Passenger(int code, String msg){
        System.out.println("main yahan hoon");
        this.code=code;
        this.msg=msg;
    }

    public Passenger(){

    }

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

     public int getcode() {
        return code;
    }

     public String getmsg() {
        return msg;
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

    public void setcode() {
        this.code=code;
    }

    public void setmsg() {
        this.msg=msg;
    }

}
