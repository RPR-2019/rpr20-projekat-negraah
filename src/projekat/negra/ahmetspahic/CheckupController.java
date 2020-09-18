package projekat.negra.ahmetspahic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CheckupController {
    public CheckBox engineCheck;
    public CheckBox brakeCheck;
    public CheckBox emissionsCheck;
    public CheckBox accumulatorCheck;
    public CheckBox electronicsCheck;
    public CheckBox ligtingCheck;
    public Button btnOk;
    public Button btnCancel;
    public Label modelLabel;
    public Label manufacturerLabel;
    public Label platesLabel;
    public Label ownerLabel;
    public Label categoryLabel;
    private Vehicle vehicle;
    private VehiclesDAO dao;
    private VehicleCheckup checkup;

    public CheckupController(Vehicle vehicle) {
        dao = VehiclesDAO.getInstance();
        this.vehicle = vehicle;
        this.checkup = null;
    }

    public CheckupController(VehicleCheckup checkup) {
        dao = VehiclesDAO.getInstance();
        this.checkup = checkup;
        this.vehicle = checkup.getVehicle();

    }

    @FXML
    public void initialize() {
        modelLabel.setText("Model: " + vehicle.getModel());
        manufacturerLabel.setText("Manufacturer: " + vehicle.getManufacturer());
        platesLabel.setText("Plates: " + vehicle.getPlates());
        ownerLabel.setText("First and last name: " + vehicle.getOwner().getFirstName() + " " + vehicle.getOwner().getLastName());
        categoryLabel.setText("Category: " + vehicle.getCategory().toString());

        if (checkup != null) {
            if(checkup.isPassedEngine()) engineCheck.fire();
            if(checkup.isPassedBrakes()) brakeCheck.fire();
            if(checkup.isPassedEmissions()) emissionsCheck.fire();
            if(checkup.isPassedAccumulator()) accumulatorCheck.fire();
            if(checkup.isPassedElectronics()) electronicsCheck.fire();
            if(checkup.isPassedLighting()) ligtingCheck.fire();

            engineCheck.setDisable(true);
            brakeCheck.setDisable(true);
            emissionsCheck.setDisable(true);
            accumulatorCheck.setDisable(true);
            electronicsCheck.setDisable(true);
            ligtingCheck.setDisable(true);
        }

    }

    public void clickOk(ActionEvent actionEvent) {
        if(checkup != null) closeWindow();

        VehicleCheckup checkup = new VehicleCheckup();
        checkup.setCheckupTime(LocalDate.now());
        checkup.setPassedEngine(engineCheck.isSelected());
        checkup.setPassedBrakes(brakeCheck.isSelected());
        checkup.setPassedEmissions(emissionsCheck.isSelected());
        checkup.setPassedAccumulator(accumulatorCheck.isSelected());
        checkup.setPassedElectronics(electronicsCheck.isSelected());
        checkup.setPassedLighting(ligtingCheck.isSelected());
        checkup.setVehicle(vehicle);

        dao.addVehicleCheckup(checkup);

        closeWindow();
    }

    public void clickCancel(ActionEvent actionEvent){
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) categoryLabel.getScene().getWindow();
        stage.close();
    }
}
