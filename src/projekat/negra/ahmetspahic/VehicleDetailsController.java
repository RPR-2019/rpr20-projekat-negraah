package projekat.negra.ahmetspahic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class VehicleDetailsController {
    public TextField fldPlates;
    public TextField fldModel;
    public TextField fldManufacturer;
    public ChoiceBox<Vehicle.Category> choiceCategory;
    public ChoiceBox<VehicleOwner> choiceOwner;
    public ListView<VehicleCheckup> lvlCheckups;
    private ObservableList<Vehicle.Category> listCategory;
    private ObservableList<VehicleOwner> ownerList;
    private ObservableList<VehicleCheckup> checkupList;
    private Vehicle vehicle;
    private VehiclesDAO dao;

    public VehicleDetailsController(Vehicle vehicle) {

        dao = VehiclesDAO.getInstance();
        this.vehicle = vehicle;
        listCategory = FXCollections.observableArrayList(Vehicle.Category.values());
        ownerList =FXCollections.observableArrayList(dao.getVehicleOwnerList());
        if(vehicle!=null)checkupList = FXCollections.observableArrayList(vehicle.getCheckups());

    }

    @FXML
    public void initialize() {

        lvlCheckups.setItems(checkupList);
        choiceCategory.setItems(listCategory);
        choiceOwner.setItems(ownerList);

        if(vehicle != null){
            fldPlates.setText(vehicle.getPlates());
            fldManufacturer.setText(vehicle.getManufacturer());
            fldModel.setText(vehicle.getModel());
            choiceCategory.getSelectionModel().select(vehicle.getCategory());
            choiceOwner.getSelectionModel().select(vehicle.getOwner());
        }

    }

    public void addVehicle(){
        vehicle = new Vehicle();
        vehicle.setPlates(fldPlates.getText());
        vehicle.setModel(fldModel.getText());
        vehicle.setManufacturer(fldManufacturer.getText());
        vehicle.setCategory(choiceCategory.getValue());
        vehicle.setOwner(choiceOwner.getValue());

        dao.addVehicle(vehicle);
    }

    public void updateVehicle(){
        vehicle.setPlates(fldPlates.getText());
        vehicle.setModel(fldModel.getText());
        vehicle.setManufacturer(fldManufacturer.getText());
        vehicle.setCategory(choiceCategory.getValue());
        vehicle.setOwner(choiceOwner.getValue());

        dao.updateVehicle(vehicle);
    }

    public void clickOk(ActionEvent actionEvent){
        if(vehicle==null){
            addVehicle();
        }else{
            updateVehicle();
        }

        closeWindow();
    }
    public void clickCancel(ActionEvent actionEvent){closeWindow();}

    private void closeWindow() {
        Stage stage = (Stage) fldModel.getScene().getWindow();
        stage.close();
    }

    public Vehicle getVehicle(){
        return vehicle;
    }
}
