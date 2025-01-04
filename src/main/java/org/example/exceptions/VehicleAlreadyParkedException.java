package org.example.exceptions;

public class VehicleAlreadyParkedException extends Throwable {
    public VehicleAlreadyParkedException(String vehicleAlreadyPresent) {
        super(vehicleAlreadyPresent);
    }
}
