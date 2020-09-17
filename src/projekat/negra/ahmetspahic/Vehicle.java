package projekat.negra.ahmetspahic;

import java.util.ArrayList;

public class Vehicle {
    public enum Category{
        A, B, C, D, BE, CE, DE
    }
    private int id;
    private String plates;
    private String model;
    private String manufacturer;
    private Category category;
    private VehicleOwner owner;
    private ArrayList<VehicleCheckup> checkups;

    public Vehicle() {
    }

    public Vehicle(int id, String plates, String model, String manufacturer, Category category, VehicleOwner owner) {
        this.id = id;
        this.plates = plates;
        this.model = model;
        this.manufacturer = manufacturer;
        this.category = category;
        this.owner = owner;
        this.checkups = new ArrayList<>();
    }

    public Vehicle(int id, String plates, String model, String manufacturer, String category, VehicleOwner owner) throws WrongCategoryException {
        this.id = id;
        this.plates = plates;
        this.model = model;
        this.manufacturer = manufacturer;
        this.category = stringToCategory(category);
        this.owner = owner;
        this.checkups = new ArrayList<>();
    }

    private Category stringToCategory(String category) throws WrongCategoryException {
        if(category.equals("A")){
            return Category.A;
        }else if(category.equals("B")){
            return Category.B;
        }else if(category.equals("C")){
            return Category.C;
        }else if(category.equals("D")){
            return Category.D;
        }else if(category.equals("BE")){
            return Category.BE;
        }else if(category.equals("CE")){
            return Category.CE;
        }else if(category.equals("DE")){
            return Category.DE;
        }else{
            throw new WrongCategoryException("Please, enter value category");
        }
    }

    public Vehicle(int id, String plates, String model, String manufacturer, String category, VehicleOwner owner, ArrayList<VehicleCheckup> checkups) throws WrongCategoryException {
        this.id = id;
        this.plates = plates;
        this.model = model;
        this.manufacturer = manufacturer;
        this.category = stringToCategory(category);
        this.owner = owner;
        this.checkups = checkups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLastCheckupString() {
        if(this.checkups.isEmpty()) return "Never checked up";
        return this.checkups.stream().reduce( (acc, x) -> acc.getCheckupTime().isAfter(x.getCheckupTime()) ?  acc : x).get().getCheckupTime().toString();
    }
}
