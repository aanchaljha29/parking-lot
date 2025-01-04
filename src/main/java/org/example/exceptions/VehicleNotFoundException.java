package org.example.exceptions;

public class VehicleNotFoundException extends Throwable {
    public VehicleNotFoundException(String vehicleNotFound) {
        super(vehicleNotFound);
    }
}
