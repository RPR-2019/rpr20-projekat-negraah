package projekat.negra.ahmetspahic;

import java.time.LocalDate;

public class VehicleCheckup {
    private int id;
    private Vehicle vehicle;
    private LocalDate checkupTime;
    private boolean passedEngine;
    private boolean passedBrakes;
    private boolean passedEmissions;
    private boolean passedAccumulator;
    private boolean passedElectronics;
    private boolean passedLighting;

    public VehicleCheckup() {
    }

    public VehicleCheckup(int id, Vehicle vehicle, LocalDate checkupTime, boolean passedEngine, boolean passedBrakes, boolean passedEmissions, boolean passedAccumulator, boolean passedElectronics, boolean passedLighting) {
        this.id = id;
        this.vehicle = vehicle;
        this.checkupTime = checkupTime;
        this.passedEngine = passedEngine;
        this.passedBrakes = passedBrakes;
        this.passedEmissions = passedEmissions;
        this.passedAccumulator = passedAccumulator;
        this.passedElectronics = passedElectronics;
        this.passedLighting = passedLighting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getCheckupTime() {
        return checkupTime;
    }

    public void setCheckupTime(LocalDate checkupTime) {
        this.checkupTime = checkupTime;
    }

    public boolean isPassedEngine() {
        return passedEngine;
    }

    public void setPassedEngine(boolean passedEngine) {
        this.passedEngine = passedEngine;
    }

    public boolean isPassedBrakes() {
        return passedBrakes;
    }

    public void setPassedBrakes(boolean passedBrakes) {
        this.passedBrakes = passedBrakes;
    }

    public boolean isPassedEmissions() {
        return passedEmissions;
    }

    public void setPassedEmissions(boolean passedEmissions) {
        this.passedEmissions = passedEmissions;
    }

    public boolean isPassedAccumulator() {
        return passedAccumulator;
    }

    public void setPassedAccumulator(boolean passedAccumulator) {
        this.passedAccumulator = passedAccumulator;
    }

    public boolean isPassedElectronics() {
        return passedElectronics;
    }

    public void setPassedElectronics(boolean passedElectronics) {
        this.passedElectronics = passedElectronics;
    }

    public boolean isPassedLighting() {
        return passedLighting;
    }

    public void setPassedLighting(boolean passedLighting) {
        this.passedLighting = passedLighting;
    }
}
