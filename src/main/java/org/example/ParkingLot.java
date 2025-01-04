package org.example;

import org.example.exceptions.SpaceNotAvailableException;
import org.example.exceptions.VehicleAlreadyParkedException;
import org.example.exceptions.VehicleNotFoundException;
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

    public double parkVehicle(Vehicle vehicle) throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        validateParkingPossible(vehicle);
        setAvailableSpace(this.availableSpace - vehicle.getSlotsRequired());
        this.vehicles.add(vehicle);
        return this.availableSpace;
    }

    private void validateParkingPossible(Vehicle vehicle) throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        if (this.vehicles.contains(vehicle)) {
            throw new VehicleAlreadyParkedException("Vehicle already present");
        }
        if (this.availableSpace - vehicle.getSlotsRequired() < 0) {
            throw new SpaceNotAvailableException("Parking Lot Full");
        }
    }

    public double unParkVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        validateVehiclePresentToUnpark(vehicle);
        this.vehicles.remove(vehicle);
        setAvailableSpace(this.availableSpace + vehicle.getSlotsRequired());
        return this.availableSpace;
    }

    private void validateVehiclePresentToUnpark(Vehicle vehicle) throws VehicleNotFoundException {
        if (!vehicles.contains(vehicle)) {
            throw new VehicleNotFoundException("Vehicle Not Found");
        }
    }
}
