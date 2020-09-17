package projekat.negra.ahmetspahic;

import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class VehiclesDAO {
    private static VehiclesDAO instance;
    private Connection conn;

    private PreparedStatement glavniGradUpit, dajDrzavuUpit, obrisiDrzavuUpit, obrisiGradoveZaDrzavuUpit, nadjiDrzavuUpit,
            dajGradoveUpit, dodajGradUpit, odrediIdGradaUpit, dodajDrzavuUpit, odrediIdDrzaveUpit, promijeniGradUpit, dajGradUpit,
            nadjiGradUpit, obrisiGradUpit, dajDrzaveUpit, dodajLetoveUpit, odrediIdLetaUpit,dajLetoveUpit;


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

        try {
            glavniGradUpit = conn.prepareStatement("SELECT grad.id, grad.naziv, grad.broj_stanovnika, grad.drzava FROM grad, drzava WHERE grad.drzava=drzava.id AND drzava.naziv=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                glavniGradUpit = conn.prepareStatement("SELECT grad.id, grad.naziv, grad.broj_stanovnika, grad.drzava FROM grad, drzava WHERE grad.drzava=drzava.id AND drzava.naziv=?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            dajDrzavuUpit = conn.prepareStatement("SELECT * FROM drzava WHERE id=?");
            dajGradUpit = conn.prepareStatement("SELECT * FROM grad WHERE id=?");
            obrisiGradoveZaDrzavuUpit = conn.prepareStatement("DELETE FROM grad WHERE drzava=?");
            obrisiDrzavuUpit = conn.prepareStatement("DELETE FROM drzava WHERE id=?");
            obrisiGradUpit = conn.prepareStatement("DELETE FROM grad WHERE id=?");
            nadjiDrzavuUpit = conn.prepareStatement("SELECT * FROM drzava WHERE naziv=?");
            nadjiGradUpit = conn.prepareStatement("SELECT * FROM grad WHERE naziv=?");
            dajGradoveUpit = conn.prepareStatement("SELECT * FROM grad ORDER BY broj_stanovnika DESC");
            dajDrzaveUpit = conn.prepareStatement("SELECT * FROM drzava ORDER BY naziv");

            dodajGradUpit = conn.prepareStatement("INSERT INTO grad VALUES(?,?,?,?)");
            odrediIdGradaUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM grad");
            dodajDrzavuUpit = conn.prepareStatement("INSERT INTO drzava VALUES(?,?,?)");
            odrediIdDrzaveUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM drzava");

            //dodajLetoveUpit = conn.prepareStatement("INSERT INTO letovi VALUES(?,?,?)");
            //odrediIdLetaUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM letovi");
            //dajLetoveUpit = conn.prepareStatement("SELECT * FROM letovi");

            promijeniGradUpit = conn.prepareStatement("UPDATE grad SET naziv=?, broj_stanovnika=?, drzava=? WHERE id=?");

            // RAZMAK ///////////////////////////////////////////////////

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

    // Metoda za potrebe testova, vraća bazu podataka u polazno stanje
    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM vehicle");
        stmt.executeUpdate("DELETE FROM owner");
        stmt.executeUpdate("DELETE FROM checkup");
        regenerisiBazu();
    }

    public Grad glavniGrad(String drzava) {
        try {
            Drzava d = nadjiDrzavu(drzava);
            glavniGradUpit.setString(1, drzava);
            ResultSet rs = glavniGradUpit.executeQuery();
            if (!rs.next()) return null;
            return dajGradIzResultSeta(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Grad dajGradIzResultSeta(ResultSet rs, Drzava d) throws SQLException {
        return new Grad(rs.getInt(1), rs.getString(2), rs.getInt(3), d);
    }

    private Drzava dajDrzavu(int id) {
        try {
            dajDrzavuUpit.setInt(1, id);
            ResultSet rs = dajDrzavuUpit.executeQuery();
            if (!rs.next()) return null;
            return dajDrzavuIzResultSeta(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Grad dajGrad(int id, Drzava d) {
        try {
            dajGradUpit.setInt(1, id);
            ResultSet rs = dajGradUpit.executeQuery();
            if (!rs.next()) return null;
            return dajGradIzResultSeta(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Drzava dajDrzavuIzResultSeta(ResultSet rs) throws SQLException {
        Drzava d = new Drzava(rs.getInt(1), rs.getString(2), null);
        d.setGlavniGrad( dajGrad(rs.getInt(3), d ));
        return d;
    }

    public void obrisiDrzavu(String nazivDrzave) {
        try {
            nadjiDrzavuUpit.setString(1, nazivDrzave);
            ResultSet rs = nadjiDrzavuUpit.executeQuery();
            if (!rs.next()) return;
            Drzava drzava = dajDrzavuIzResultSeta(rs);

            obrisiGradoveZaDrzavuUpit.setInt(1, drzava.getId());
            obrisiGradoveZaDrzavuUpit.executeUpdate();

            obrisiDrzavuUpit.setInt(1, drzava.getId());
            obrisiDrzavuUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> rezultat = new ArrayList();
        try {
            ResultSet rs = dajGradoveUpit.executeQuery();
            while (rs.next()) {
                Drzava d = dajDrzavu(rs.getInt(4));
                Grad grad = dajGradIzResultSeta(rs, d);
                rezultat.add(grad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Drzava> drzave() {
        ArrayList<Drzava> rezultat = new ArrayList();
        try {
            ResultSet rs = dajDrzaveUpit.executeQuery();
            while (rs.next()) {
                Drzava drzava = dajDrzavuIzResultSeta(rs);
                rezultat.add(drzava);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }


    public void dodajGrad(Grad grad) {
        try {
            ResultSet rs = odrediIdGradaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajGradUpit.setInt(1, id);
            dodajGradUpit.setString(2, grad.getNaziv());
            dodajGradUpit.setInt(3, grad.getBrojStanovnika());
            dodajGradUpit.setInt(4, grad.getDrzava().getId());
            dodajGradUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            ResultSet rs = odrediIdDrzaveUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajDrzavuUpit.setInt(1, id);
            dodajDrzavuUpit.setString(2, drzava.getNaziv());
            dodajDrzavuUpit.setInt(3, drzava.getGlavniGrad().getId());
            dodajDrzavuUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniGrad(Grad grad) {
        try {
            promijeniGradUpit.setString(1, grad.getNaziv());
            promijeniGradUpit.setInt(2, grad.getBrojStanovnika());
            promijeniGradUpit.setInt(3, grad.getDrzava().getId());
            promijeniGradUpit.setInt(4, grad.getId());
            promijeniGradUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Drzava nadjiDrzavu(String nazivDrzave) {
        try {
            nadjiDrzavuUpit.setString(1, nazivDrzave);
            ResultSet rs = nadjiDrzavuUpit.executeQuery();
            if (!rs.next()) return null;
            return dajDrzavuIzResultSeta(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Grad nadjiGrad(String nazivGrada) {
        try {
            nadjiGradUpit.setString(1, nazivGrada);
            ResultSet rs = nadjiGradUpit.executeQuery();
            if (!rs.next()) return null;
            Drzava d = dajDrzavu(rs.getInt(4));
            return dajGradIzResultSeta(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void obrisiGrad(Grad grad) {
        try {
            obrisiGradUpit.setInt(1, grad.getId());
            obrisiGradUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        return new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), owner);
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
            getOwnerQuery.setInt(1, id);
            ResultSet rs = getOwnerQuery.executeQuery();
            if (!rs.next()) return;
            VehicleOwner owner = getOwnerFromResultSet(rs);

            deleteVehiclesByOwnerQuery.setInt(1, owner.getId());
            deleteVehiclesByOwnerQuery.executeUpdate();

            deleteOwnerQuery.setInt(1, owner.getId());
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
            updateOwnerQuery.setDate(3, null/*Date.valueOf(owner.getDateOfBirth())*/);
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
