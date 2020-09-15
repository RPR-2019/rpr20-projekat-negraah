package projekat.negra.ahmetspahic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class VehicleOwnerController {
    public GridPane glavni;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField upinField;
    public TextField adressField;
    public TextField phoneField;
    public ListView<VehicleOwner> listKorisnici;

    private OwnerModel model;

    public VehicleOwnerController(OwnerModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getAllOwners());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setCurrentOwner(newKorisnik);
            listKorisnici.refresh();
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

    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getAllOwners().add(new VehicleOwner(-1, "", "", null, 0,  "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void obrisiAction(ActionEvent actionEvent){
        model.deleteCurrent();
    }

    public void sacuvajAction(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File f = fileChooser.showSaveDialog(glavni.getScene().getWindow());
        model.zapisiDatoteku(f);
    }

    public void staviBosanski(ActionEvent actionEvent){
        Locale locale = new Locale("bs", "BA");
        Stage primaryStage = (Stage) glavni.getScene().getWindow();
        Locale.setDefault(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnici.fxml"), bundle);
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
    }

    public void staviEngleski(ActionEvent actionEvent){
        Locale locale = new Locale("bs", "BA");
        Stage primaryStage = (Stage) glavni.getScene().getWindow();
        Locale.setDefault(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnici.fxml"), bundle);
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
    }
}
