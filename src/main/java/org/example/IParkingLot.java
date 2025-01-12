package org.example;

import org.example.exceptions.ParkingNotPossibleException;
import org.example.exceptions.UnparkingNotPossible;
import org.example.model.Receipt;
import org.example.vehicle.Vehicle;

public interface IParkingLot {
    Receipt parkVehicle(Vehicle vehicle, boolean isNearLift) throws ParkingNotPossibleException;
    Receipt unParkVehicle(Receipt receipt) throws UnparkingNotPossible;
}
