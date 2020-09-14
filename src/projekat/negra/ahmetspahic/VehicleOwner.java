package projekat.negra.ahmetspahic;

import java.time.LocalDate;

public class VehicleOwner {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Integer upin; //Unique Personal Identification Number
    private String adress;
    private String phoneNumber;

    public VehicleOwner() {
    }

    public VehicleOwner(int id, String firstName, String lastName, LocalDate dateOfBirth, Integer upin, String adress, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.upin = upin;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getUpin() {
        return upin;
    }

    public void setUpin(Integer upin) {
        this.upin = upin;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
