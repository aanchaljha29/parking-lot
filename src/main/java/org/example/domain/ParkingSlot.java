package org.example.domain;

import org.example.vehicle.Vehicle;

import java.util.Objects;

public class ParkingSlot {
    private final String slotId;
    private final boolean isNearLift;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSlot(final String slotId, final boolean isNearLift) {
        this.slotId = slotId;
        this.isNearLift = isNearLift;
    }

    public ParkingSlot(final String slotId, final boolean isNearLift, final boolean isOccupied, final Vehicle parkedVehicle) {
        this.slotId = slotId;
        this.isNearLift = isNearLift;
        this.isOccupied = isOccupied;
        this.vehicle = parkedVehicle;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public String getSlotId() {
        return this.slotId;
    }

    public boolean isNearLift() {
        return this.isNearLift;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }

    public ParkingSlot parkVehicle(final Vehicle vehicle) {
        return new ParkingSlot(this.slotId, this.isNearLift, true, vehicle);
    }

    public ParkingSlot unParkVehicle() {
        return new ParkingSlot(this.slotId, this.isNearLift, false, null);
    }

    public boolean canPark(Vehicle vehicle) {
        return !this.isOccupied && !this.isVehicleAlreadyParked(vehicle);
    }

    public boolean isVehicleAlreadyParked(Vehicle vehicle) {
        return this.vehicle != null && this.vehicle.equals(vehicle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return isOccupied == that.isOccupied &&
                Objects.equals(slotId, that.slotId) &&
                Objects.equals(vehicle, that.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotId); // Ensures that slots with the same slotId have the same hash code
    }

}
