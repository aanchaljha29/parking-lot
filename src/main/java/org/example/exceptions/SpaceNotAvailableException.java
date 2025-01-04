package org.example.exceptions;

public class SpaceNotAvailableException extends Throwable {
    public SpaceNotAvailableException(String parkingLotFull) {
        super(parkingLotFull);
    }
}
