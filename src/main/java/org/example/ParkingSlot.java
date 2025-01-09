package org.example;

import org.example.vehicle.Vehicle;

public class ParkingSlot {
    private final int slotId;
    private final boolean isNearLift;
    private boolean isOccupied;

    private Vehicle vehicle;

    public ParkingSlot(int slotId, boolean isNearLift) {
        this.slotId = slotId;
        this.isNearLift = isNearLift;
    }

    public int getSlotId() {
        return slotId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isNearLift() {
        return isNearLift;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.isOccupied = true;
        this.vehicle = vehicle;
    }

    public void unParkVehicle() {
        this.isOccupied = false;
        this.vehicle = null;
    }
}
