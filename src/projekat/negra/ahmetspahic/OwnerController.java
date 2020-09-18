package projekat.negra.ahmetspahic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.util.Locale;
import java.util.ResourceBundle;

public class OwnerController {
    public GridPane main;
    public DatePicker datePicker;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField upinField;
    public TextField adressField;
    public TextField phoneField;
    public Button btnAdd;
    private VehiclesDAO dao;
    private ObservableList<VehicleOwner> ownerList;
    public ListView<VehicleOwner> ownersListView;
    private OwnerModel model;

    public OwnerController(OwnerModel model) {
        dao = VehiclesDAO.getInstance();
        this.model = model;
        ownerList = FXCollections.observableArrayList(dao.getVehicleOwnerList());
    }

    @FXML
    public void initialize() {
        ownersListView.setItems(ownerList);

        ownersListView.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if(model.getCurrentOwner() != null) model.getCurrentOwner().setDateOfBirth( datePicker.getValue() );
            model.setCurrentOwner(newKorisnik);
            ownersListView.refresh();
        });

        model.currentOwnerProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                firstNameField.textProperty().unbindBidirectional(oldKorisnik.firstNameProperty() );
                lastNameField.textProperty().unbindBidirectional(oldKorisnik.lastNameProperty() );
                upinField.textProperty().unbindBidirectional(oldKorisnik.upinProperty() );
                adressField.textProperty().unbindBidirectional(oldKorisnik.adressProperty());
                phoneField.textProperty().unbindBidirectional(oldKorisnik.phoneProperty() );
            }
            if (newKorisnik == null) {
                btnAdd.setDisable(true);
                firstNameField.setText("");
                lastNameField.setText("");
                upinField.setText("");
                adressField.setText("");
                datePicker.setValue(null);
                phoneField.setText("");
            }
            else {
                if(newKorisnik.getId() != 0) btnAdd.setDisable(true);
                else btnAdd.setDisable(false);
                firstNameField.textProperty().bindBidirectional( newKorisnik.firstNameProperty() );
                lastNameField.textProperty().bindBidirectional( newKorisnik.lastNameProperty() );
                upinField.textProperty().bindBidirectional(newKorisnik.upinProperty());
                adressField.textProperty().bindBidirectional(newKorisnik.adressProperty());
                datePicker.setValue( newKorisnik.getDateOfBirth() );
                phoneField.textProperty().bindBidirectional(newKorisnik.phoneProperty());
            }
        });

        firstNameField.textProperty().addListener((obs, oldIme, newIme) -> {

            if(model.getCurrentOwner() != null){
                model.getCurrentOwner().setFirstName(newIme);
                ownersListView.refresh();
            }

            if (!newIme.isEmpty()) {
                firstNameField.getStyleClass().removeAll("poljeNijeIspravno");
                firstNameField.getStyleClass().add("poljeIspravno");
            } else {
                firstNameField.getStyleClass().removeAll("poljeIspravno");
                firstNameField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        lastNameField.textProperty().addListener((obs, oldIme, newIme) -> {

            if(model.getCurrentOwner() != null){
                model.getCurrentOwner().setLastName(newIme);
                ownersListView.refresh();
            }

            if (!newIme.isEmpty()) {
                lastNameField.getStyleClass().removeAll("poljeNijeIspravno");
                lastNameField.getStyleClass().add("poljeIspravno");
            } else {
                lastNameField.getStyleClass().removeAll("poljeIspravno");
                lastNameField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        upinField.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                upinField.getStyleClass().removeAll("poljeNijeIspravno");
                upinField.getStyleClass().add("poljeIspravno");
            } else {
                upinField.getStyleClass().removeAll("poljeIspravno");
                upinField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        adressField.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                adressField.getStyleClass().removeAll("poljeNijeIspravno");
                adressField.getStyleClass().add("poljeIspravno");
            } else {
                adressField.getStyleClass().removeAll("poljeIspravno");
                adressField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        phoneField.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                phoneField.getStyleClass().removeAll("poljeNijeIspravno");
                phoneField.getStyleClass().add("poljeIspravno");
            } else {
                phoneField.getStyleClass().removeAll("poljeIspravno");
                phoneField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        ownersListView.getSelectionModel().selectFirst();

    }

    public void add(ActionEvent actionEvent) {
        ownersListView.getSelectionModel().getSelectedItem().setDateOfBirth(datePicker.getValue());
        dao.addVehicleOwner(ownersListView.getSelectionModel().getSelectedItem());
        ownerList = FXCollections.observableArrayList(dao.getVehicleOwnerList());
        ownersListView.setItems(ownerList);
        ownersListView.getSelectionModel().selectFirst();
    }

    public void ok(ActionEvent actionEvent) {
        if(model.getCurrentOwner() != null && model.getCurrentOwner().getId() != 0) model.getCurrentOwner().setDateOfBirth( datePicker.getValue() );
        model.disconnect();
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }

    public void delete(ActionEvent actionEvent){
        if(ownersListView.getSelectionModel().getSelectedItem().getId() == 0) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do  you really want to " +
                "delete owner: " + firstNameField.getText()+ " " + lastNameField.getText(), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult()==ButtonType.YES) {
            model.deleteCurrent();
            ownerList = FXCollections.observableArrayList(dao.getVehicleOwnerList());
            ownersListView.setItems(ownerList);
            ownersListView.getSelectionModel().selectFirst();
        }else{
            return;
        }
    }


}
