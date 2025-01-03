package org.example;

public class SpaceNotAvailableException extends Throwable {
    public SpaceNotAvailableException(String parkingLotFull) {
        super(parkingLotFull);
    }
}
