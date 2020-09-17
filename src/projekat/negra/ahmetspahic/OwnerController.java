package projekat.negra.ahmetspahic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class OwnerController {
    public GridPane main;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField upinField;
    public TextField adressField;
    public TextField phoneField;
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
            model.setCurrentOwner(newKorisnik);
            ownersListView.refresh();
        });

        model.currentOwnerProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                firstNameField.textProperty().unbindBidirectional(oldKorisnik.firstNameProperty() );
                lastNameField.textProperty().unbindBidirectional(oldKorisnik.lastNameProperty() );
                upinField.textProperty().unbindBidirectional(oldKorisnik.upinProperty() );
                adressField.textProperty().unbindBidirectional(oldKorisnik.adressProperty() );
                phoneField.textProperty().unbindBidirectional(oldKorisnik.phoneProperty() );
            }
            if (newKorisnik == null) {
                firstNameField.setText("");
                lastNameField.setText("");
                upinField.setText("");
                adressField.setText("");
                phoneField.setText("");
            }
            else {
                firstNameField.textProperty().bindBidirectional( newKorisnik.firstNameProperty() );
                lastNameField.textProperty().bindBidirectional( newKorisnik.lastNameProperty() );
                upinField.textProperty().bindBidirectional( newKorisnik.upinProperty() );
                adressField.textProperty().bindBidirectional( newKorisnik.adressProperty() );
                phoneField.textProperty().bindBidirectional( newKorisnik.phoneProperty() );
            }
        });

        firstNameField.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                firstNameField.getStyleClass().removeAll("poljeNijeIspravno");
                firstNameField.getStyleClass().add("poljeIspravno");
            } else {
                firstNameField.getStyleClass().removeAll("poljeIspravno");
                firstNameField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        lastNameField.textProperty().addListener((obs, oldIme, newIme) -> {
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

    }

    public void add(ActionEvent actionEvent) {
      /*  model.getAllOwners().add(new VehicleOwner("", "", "", "", ""));
        ownerList.getSelectionModel().selectLast();*/
    }

    public void ok(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void delete(ActionEvent actionEvent){
        model.deleteCurrent();
    }


}
