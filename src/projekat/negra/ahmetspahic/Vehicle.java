package projekat.negra.ahmetspahic;

import java.util.ArrayList;

public class Vehicle {
    public enum Category{
        A, B, C, D, BE, CE, DE
    }
    private String plates;
    private String model;
    private String manufacturer;
    private Category category;
    private VehicleOwner owner;
    private ArrayList<VehicleCheckup> checkups;

    public Vehicle() {
    }

    public Vehicle(String plates, String model, String manufacturer, Category category, VehicleOwner owner, ArrayList<VehicleCheckup> checkups) {
        this.plates = plates;
        this.model = model;
        this.manufacturer = manufacturer;
        this.category = category;
        this.owner = owner;
        this.checkups = checkups;
    }

    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public VehicleOwner getOwner() {
        return owner;
    }

    public void setOwner(VehicleOwner owner) {
        this.owner = owner;
    }

    public ArrayList<VehicleCheckup> getCheckups() {
        return checkups;
    }

    public void setCheckups(ArrayList<VehicleCheckup> checkups) {
        this.checkups = checkups;
    }
}
