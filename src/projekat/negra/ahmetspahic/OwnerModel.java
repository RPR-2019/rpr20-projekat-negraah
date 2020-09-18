package projekat.negra.ahmetspahic;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OwnerModel {
    private ObservableList<VehicleOwner> allOwners = FXCollections.observableArrayList();
    private SimpleObjectProperty<VehicleOwner> currentOwner = new SimpleObjectProperty<>();
    private Connection conn;

    public OwnerModel() {
        conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
        }
    }

    public void addOwners() {
            for (VehicleOwner owner : VehiclesDAO.getInstance().getVehicleOwnerList()) {
                allOwners.add(owner);
            }

        currentOwner.set(null);
    }

    public ObservableList<VehicleOwner> getAllOwners() {
        return allOwners;
    }

    public void setAllOwners(ObservableList<VehicleOwner> allOwners) {
        this.allOwners = allOwners;
    }

    public VehicleOwner getCurrentOwner() {
        return currentOwner.get();
    }

    public SimpleObjectProperty<VehicleOwner> currentOwnerProperty() {
        return currentOwner;
    }

    public void setCurrentOwner(VehicleOwner currentOwner) {
        if(this.currentOwner != null && this.currentOwner.get() != null && this.currentOwner.get().getId() != 0) {
            VehiclesDAO.getInstance().updateVehicleOwner(this.currentOwner.get());
        }
        this.currentOwner.set(currentOwner);
    }

    public void setCurrentOwner(int i) {
        if(this.currentOwner != null && this.currentOwner.get() != null && this.currentOwner.get().getId() != 0) {
            VehiclesDAO.getInstance().updateVehicleOwner(this.currentOwner.get());
        }
        this.currentOwner.set(allOwners.get(i));
    }

    public void disconnect() {
        if(this.currentOwner != null && this.currentOwner.get() != null && this.currentOwner.get().getId() != 0) {
            VehiclesDAO.getInstance().updateVehicleOwner(this.currentOwner.get());
        }
    }

    public void deleteCurrent() {
        VehiclesDAO.getInstance().deleteOwner(currentOwner.get().getId());
    }
}
