package org.example;

import org.example.exceptions.ParkingNotPossibleException;
import org.example.exceptions.UnparkingNotPossible;
import org.example.vehicle.Vehicle;

import java.util.List;

public class ParkingLot {

    private double availableSpace;

    private final List<Vehicle> vehicles;

    public ParkingLot(int availableSpace, List<Vehicle> vehicles) {
        this.availableSpace = availableSpace;
        this.vehicles = vehicles;
    }

    public void setAvailableSpace(double availableSpace) {
        this.availableSpace = availableSpace;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public double parkVehicle(Vehicle vehicle) throws ParkingNotPossibleException {
        ParkingError parkingStatus = isParkingPossible(vehicle);
        if (!parkingStatus.isPossible()) {
            throw new ParkingNotPossibleException(parkingStatus.getStatus());
        }
        setAvailableSpace(this.availableSpace - vehicle.getSlotsRequired());
        this.vehicles.add(vehicle);
        return this.availableSpace;
    }

    private ParkingError isParkingPossible(Vehicle vehicle) {
        if (this.vehicles.contains(vehicle)) {
            return new ParkingError(false, ParkingStatus.VEHICLE_PRESENT);
        }
        if (this.availableSpace - vehicle.getSlotsRequired() < 0) {
            return new ParkingError(false, ParkingStatus.SPACE_UNAVAILABLE);
        }
        return new ParkingError(true, ParkingStatus.POSSIBLE);
    }

    public double unParkVehicle(Vehicle vehicle) throws UnparkingNotPossible {
        ParkingError unParkingStatus = isUnparkingPossible(vehicle);
        if (!unParkingStatus.isPossible()) {
            throw new UnparkingNotPossible(unParkingStatus.getStatus());
        }
        this.vehicles.remove(vehicle);
        setAvailableSpace(this.availableSpace + vehicle.getSlotsRequired());
        return this.availableSpace;
    }

    private ParkingError isUnparkingPossible(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            return new ParkingError(false, ParkingStatus.VEHICLE_ABSENT);
        }
        return new ParkingError(true, ParkingStatus.POSSIBLE);
    }
}
