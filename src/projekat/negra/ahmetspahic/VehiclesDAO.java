package projekat.negra.ahmetspahic;

import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class VehiclesDAO {
    private static VehiclesDAO instance;
    private Connection conn;


    private PreparedStatement addVehicleQuery, getVehicleQuery, getVehiclesQuery, getVehiclesByOwnerQuery, getVehicleIdQuery, updateVehicleQuery,
            deleteVehicleByOwnerQuery, deleteVehicleQuery, deleteVehiclesByOwnerQuery, deleteVehiclesQuery, addOwnerQuery, getOwnerQuery,
            getOwnersQuery, getOwnerIdQuery, updateOwnerQuery, deleteOwnerQuery, deleteOwnersQuery, addCheckupQuery, getCheckupQuery,
            getCheckupsQuery, getCheckupsByVehicleQuery, getCheckupIdQuery, updateCheckupQuery, deleteCheckupQuery, deleteCheckupsQuery, deleteCheckupByVehicleQuery;

    public static VehiclesDAO getInstance() {
        if (instance == null) instance = new VehiclesDAO();
        return instance;
    }
    private VehiclesDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //regenerisiBazu();
        try {
            addVehicleQuery = conn.prepareStatement("INSERT INTO vehicle VALUES(?,?,?,?,?,?)");
            getVehicleQuery = conn.prepareStatement("SELECT * FROM vehicle WHERE id=?");
            getVehiclesQuery = conn.prepareStatement("SELECT * FROM vehicle");
            getVehiclesByOwnerQuery = conn.prepareStatement("SELECT * FROM vehicle WHERE owner_id=?");
            getVehicleIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM vehicle");
            updateVehicleQuery = conn.prepareStatement("UPDATE vehicle SET plates=?, model=?, manufacturer=?, category=?, owner_id=? WHERE id=?");
           // deleteOwnerByVehicle = conn.prepareStatement("DELETE FROM owner WHERE vehicle=?");
            deleteVehiclesByOwnerQuery = conn.prepareStatement("DELETE FROM vehicle WHERE owner_id=?");
            deleteVehicleQuery = conn.prepareStatement("DELETE FROM vehicle WHERE id=?");
            deleteVehiclesQuery = conn.prepareStatement("DELETE FROM vehicle");
            deleteVehiclesByOwnerQuery = conn.prepareStatement("DELETE FROM vehicle WHERE owner_id=?");

            addOwnerQuery = conn.prepareStatement("INSERT INTO owner VALUES(?,?,?,?,?,?,?)");
            getOwnerQuery = conn.prepareStatement("SELECT * FROM owner WHERE id=?");
            getOwnersQuery = conn.prepareStatement("SELECT * FROM owner");
            getOwnerIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM owner");
            updateOwnerQuery = conn.prepareStatement("UPDATE owner SET first_name=?, last_name=?, date_of_birth=?, upin=?, adress=?, phone_number=? WHERE id=?");
            deleteOwnerQuery = conn.prepareStatement("DELETE FROM owner WHERE id=?");
            deleteOwnersQuery = conn.prepareStatement("DELETE FROM owner");

            addCheckupQuery = conn.prepareStatement("INSERT INTO checkup VALUES(?,?,?,?,?,?,?,?,?)");
            getCheckupQuery = conn.prepareStatement("SELECT * FROM checkup WHERE id=?");
            getCheckupsQuery = conn.prepareStatement("SELECT * FROM checkup");
            getCheckupsByVehicleQuery = conn.prepareStatement("SELECT * FROM checkup WHERE vehicle_id=?");
            getCheckupIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM checkup");
            updateCheckupQuery = conn.prepareStatement("UPDATE checkup SET vehicle_id=?, checkup_time=?, passed_engine=?, passed_brakes=?, passed_emissions=?, passed_accumulator=?, passed_electronics=?, passed_lighting=? where id=?");
            deleteCheckupByVehicleQuery = conn.prepareStatement("DELETE FROM checkup WHERE vehicle_id=?");
            deleteCheckupsQuery = conn.prepareStatement("DELETE FROM checkup");
            deleteCheckupQuery = conn.prepareStatement("SELECT * FROM checkup WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM vehicle");
        stmt.executeUpdate("DELETE FROM owner");
        stmt.executeUpdate("DELETE FROM checkup");
        regenerisiBazu();
    }


    private VehicleOwner getOwnerFromResultSet(ResultSet rs) throws SQLException {
        return new VehicleOwner(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4) == null ?  null : rs.getDate(4).toLocalDate(), rs.getInt(5), rs.getString(6), rs.getString(7));
    }

    public ArrayList<VehicleOwner> getVehicleOwnerList() {
        ArrayList<VehicleOwner> resultList = new ArrayList();
        try {
            ResultSet rs = getOwnersQuery.executeQuery();
            while (rs.next()) {
                VehicleOwner owners = getOwnerFromResultSet(rs);
                resultList.add(owners);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public VehicleOwner getVehicleOwner(int id) throws SQLException {
        getOwnerQuery.setInt(1, id);
        ResultSet rs = getOwnerQuery.executeQuery();
        if(!rs.next()) return null;
        return getOwnerFromResultSet(rs);
    }

    private Vehicle getVehicleFromResultSet(ResultSet rs) throws SQLException, WrongCategoryException {
        VehicleOwner owner = getVehicleOwner(rs.getInt(6));
        getCheckupsByVehicleQuery.setInt(1, rs.getInt(1));
        ResultSet rs1 = getCheckupsByVehicleQuery.executeQuery();

        ArrayList<VehicleCheckup> checkups;
        Vehicle vehicle = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), owner, new ArrayList<>());

        while(rs1.next()){
            vehicle.getCheckups().add(new VehicleCheckup(rs1.getInt(1), vehicle, rs1.getDate(3).toLocalDate(), rs1.getBoolean(4), rs1.getBoolean(5), rs1.getBoolean(6), rs1.getBoolean(7), rs1.getBoolean(8), rs1.getBoolean(9)));
        }
        return vehicle;
    }

    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> resultList = new ArrayList();
        try {
            ResultSet rs = getVehiclesQuery.executeQuery();
            while (rs.next()) {
                Vehicle vehicles = getVehicleFromResultSet(rs);
                resultList.add(vehicles);
            }
        } catch (SQLException | WrongCategoryException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private Vehicle getVehicle(int id) throws SQLException, WrongCategoryException {
        getVehicleQuery.setInt(1, id);
        ResultSet rs = getVehicleQuery.executeQuery();
        if(!rs.next()) return null;
        return getVehicleFromResultSet(rs);
    }

    private VehicleCheckup getCheckupFromResultSet(ResultSet rs) throws SQLException, WrongCategoryException {
        Vehicle vehicle = getVehicle(rs.getInt(2));
        return new VehicleCheckup(rs.getInt(1), vehicle, rs.getDate(3).toLocalDate(), rs.getBoolean(4), rs.getBoolean(5), rs.getBoolean(6), rs.getBoolean(7), rs.getBoolean(8), rs.getBoolean(9));
    }

    public ArrayList<VehicleCheckup> getVehicleCheckupList() {
        ArrayList<VehicleCheckup> resultList = new ArrayList();
        try {
            ResultSet rs = getCheckupsQuery.executeQuery();
            while (rs.next()) {
                VehicleCheckup checkups = getCheckupFromResultSet(rs);
                resultList.add(checkups);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (WrongCategoryException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public void addVehicle(Vehicle vehicle) {
        try {
            ResultSet rs = getVehicleIdQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            vehicle.setId(id);

            addVehicleQuery.setInt(1, id);
            addVehicleQuery.setString(2, vehicle.getPlates());
            addVehicleQuery.setString(3, vehicle.getModel());
            addVehicleQuery.setString(4, vehicle.getManufacturer());
            addVehicleQuery.setString(5, vehicle.getCategory().toString());
            addVehicleQuery.setInt(6, vehicle.getOwner().getId());
            addVehicleQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addVehicleCheckup(VehicleCheckup checkup) {
        try {
            ResultSet rs = getCheckupIdQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            checkup.setId(id);

            addCheckupQuery.setInt(1, id);
            addCheckupQuery.setInt(2, checkup.getVehicle().getId());
            addCheckupQuery.setDate(3, Date.valueOf(checkup.getCheckupTime()));
            addCheckupQuery.setBoolean(4, checkup.isPassedEngine());
            addCheckupQuery.setBoolean(5, checkup.isPassedBrakes());
            addCheckupQuery.setBoolean(6, checkup.isPassedEmissions());
            addCheckupQuery.setBoolean(7, checkup.isPassedAccumulator());
            addCheckupQuery.setBoolean(8, checkup.isPassedElectronics());
            addCheckupQuery.setBoolean(9, checkup.isPassedLighting());

            addCheckupQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addVehicleOwner(VehicleOwner owner) {
        try {
            ResultSet rs = getOwnerIdQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            owner.setId(id);

            addOwnerQuery.setInt(1, id);
            addOwnerQuery.setString(2, owner.getFirstName());
            addOwnerQuery.setString(3, owner.getLastName());
            addOwnerQuery.setDate(4, Date.valueOf(owner.getDateOfBirth()));
            addOwnerQuery.setInt(5, owner.getUpin());
            addOwnerQuery.setString(6, owner.getAdress());
            addOwnerQuery.setString(7, owner.getPhoneNumber());

            addOwnerQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public void deleteVehicle(int id) {
            try {
                deleteVehicleQuery.setInt(1, id);
                deleteVehicleQuery.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

       public void deleteOwner(int id) {
        try {

            deleteVehiclesByOwnerQuery.setInt(1, id);
            deleteVehiclesByOwnerQuery.executeUpdate();

            deleteOwnerQuery.setInt(1, id);
            deleteOwnerQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicleCheckup(int id) {
        try {
            getCheckupQuery.setInt(1, id);
            ResultSet rs = getCheckupQuery.executeQuery();
            if (!rs.next()) return;
            VehicleCheckup checkup = getCheckupFromResultSet(rs);

            deleteCheckupByVehicleQuery.setInt(1, checkup.getId());
            deleteCheckupByVehicleQuery.executeUpdate();

            deleteCheckupQuery.setInt(1, checkup.getId());
            deleteCheckupQuery.executeUpdate();
        } catch (SQLException | WrongCategoryException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicles(){
        try {
            deleteVehiclesQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOwners(){
        try {
            deleteOwnersQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCheckups(){
        try {
            deleteCheckupsQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicle(Vehicle vehicle) {
        try {
            updateVehicleQuery.setString(1, vehicle.getPlates());
            updateVehicleQuery.setString(2, vehicle.getModel());
            updateVehicleQuery.setString(3, vehicle.getManufacturer());
            updateVehicleQuery.setString(4, vehicle.getCategory().toString());
            updateVehicleQuery.setInt(5, vehicle.getOwner().getId());
            updateVehicleQuery.setInt(6, vehicle.getId());
            updateVehicleQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicleOwner(VehicleOwner owner) {
        try {
            updateOwnerQuery.setString(1, owner.getFirstName());
            updateOwnerQuery.setString(2, owner.getLastName());
            updateOwnerQuery.setDate(3, owner.getDateOfBirth()==null ? null : Date.valueOf(owner.getDateOfBirth()));
            updateOwnerQuery.setInt(4, owner.getUpin());
            updateOwnerQuery.setString(5, owner.getAdress());
            updateOwnerQuery.setString(6, owner.getPhoneNumber());
            updateOwnerQuery.setInt(7, owner.getId());
            updateOwnerQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicleCheckup(VehicleCheckup checkup) {
        try {
            updateCheckupQuery.setInt(1, checkup.getVehicle().getId());
            updateCheckupQuery.setDate(2, Date.valueOf(checkup.getCheckupTime()));
            updateCheckupQuery.setBoolean(3, checkup.isPassedEngine());
            updateCheckupQuery.setBoolean(4, checkup.isPassedBrakes());
            updateCheckupQuery.setBoolean(5, checkup.isPassedEmissions());
            updateCheckupQuery.setBoolean(6, checkup.isPassedAccumulator());
            updateCheckupQuery.setBoolean(7, checkup.isPassedElectronics());
            updateCheckupQuery.setBoolean(8, checkup.isPassedLighting());
            updateCheckupQuery.setInt(9, checkup.getId());

            updateCheckupQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
