package projekat.negra.ahmetspahic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class VehicleOwner {
    private int id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private LocalDate dateOfBirth;
    private SimpleStringProperty upin; //Unique Personal Identification Number
    private SimpleStringProperty adress;
    private SimpleStringProperty phoneNumber;

    public VehicleOwner() {
    }

    public VehicleOwner(int id, String firstName, String lastName, LocalDate dateOfBirth, Integer upin, String adress, String phoneNumber) {
        this.id = id;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = dateOfBirth;
        this.upin = new SimpleStringProperty(upin.toString());
        this.adress = new SimpleStringProperty(adress);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public VehicleOwner(String s, String s1, String s2, String s3, String s4) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getUpin() {
        return Integer.valueOf(upin.get());
    }

    public void setUpin(Integer upin) {
        this.upin.set(upin.toString());
    }

    public String getAdress() {
        return adress.get();
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
    
    public SimpleStringProperty firstNameProperty(){return firstName;}

    public SimpleStringProperty lastNameProperty(){return lastName;}

    public SimpleStringProperty upinProperty(){return upin;}

    public SimpleStringProperty adressProperty(){return adress;}

    public SimpleStringProperty phoneProperty(){return phoneNumber;}

}
