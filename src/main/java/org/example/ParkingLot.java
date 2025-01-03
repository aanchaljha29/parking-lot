package org.example;

public class ParkingLot {

    private int availableSpace;

    public ParkingLot(int availableSpace) {
        this.availableSpace = availableSpace;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(int availableSpace) {
        this.availableSpace = availableSpace;
    }

    public int parkVehicle() throws SpaceNotAvailableException {
        if (getAvailableSpace() <= 0) {
            throw new SpaceNotAvailableException("Parking Lot full");
        }
        setAvailableSpace(this.availableSpace - 1);
        System.out.println("Vehicle parked");
        return this.availableSpace;
    }

    public int unParkVehicle() throws SpaceNotAvailableException {
        setAvailableSpace(this.availableSpace + 1);
        System.out.println("Vehicle unParked");
        return this.availableSpace;
    }
}
