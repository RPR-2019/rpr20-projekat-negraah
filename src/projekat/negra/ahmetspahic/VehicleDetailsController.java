package projekat.negra.ahmetspahic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;


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
        ownerList =FXCollections.observableArrayList(dao.getVehicleOwnerList().stream().filter(x -> x.getId() != 0).collect(Collectors.toList()));
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

        lvlCheckups.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if(lvlCheckups.getSelectionModel().getSelectedItem() != null){
                Stage checkupStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/checkup.fxml"));
                CheckupController ctrl = new CheckupController(lvlCheckups.getSelectionModel().getSelectedItem());
                loader.setController(ctrl);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkupStage.setTitle(lvlCheckups.getSelectionModel().getSelectedItem().toString());
                checkupStage.setScene(new Scene(root, 600, 400));
                checkupStage.show();
            }
        });

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
