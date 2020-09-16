package projekat.negra.ahmetspahic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

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

    public CheckupController(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    public void initialize() {
        modelLabel.setText("Model: " + vehicle.getModel());
        manufacturerLabel.setText("Manufacturer: " + vehicle.getManufacturer());
        platesLabel.setText("Plates: " + vehicle.getPlates());
        ownerLabel.setText("First and last name: " + vehicle.getOwner().getFirstName() +" "+ vehicle.getOwner().getLastName());
        categoryLabel.setText("Category: " + vehicle.getCategory().toString());
    }

    public void clickOk(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void clickCancel(ActionEvent actionEvent){
        System.exit(0);
    }
}
