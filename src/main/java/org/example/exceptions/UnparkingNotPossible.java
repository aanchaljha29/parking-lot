package org.example.exceptions;

public class UnparkingNotPossible extends Throwable {
    public UnparkingNotPossible(String vehicleNotFound) {
        super(vehicleNotFound);
    }
}
