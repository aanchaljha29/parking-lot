package org.example.exceptions;

public class ParkingNotPossibleException extends Throwable {
    public ParkingNotPossibleException(String parkingStatus) {
        super(parkingStatus);
    }
}
