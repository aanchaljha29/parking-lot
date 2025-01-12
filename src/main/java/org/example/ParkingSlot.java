package org.example;

import org.example.exceptions.ParkingNotPossibleException;
import org.example.exceptions.UnparkingNotPossible;
import org.example.vehicle.Vehicle;

public class ParkingSlot {
    private final String slotId;
    private final boolean isNearLift;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSlot(final String slotId, final boolean isNearLift) {
        this.slotId = slotId;
        this.isNearLift = isNearLift;
    }

    private ParkingSlot(final String slotId, final boolean isNearLift, final boolean isOccupied, final Vehicle parkedVehicle) {
        this.slotId = slotId;
        this.isNearLift = isNearLift;
        this.isOccupied = isOccupied;
        this.vehicle = parkedVehicle;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public boolean isNearLift() {
        return this.isNearLift;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }

    public ParkingSlot parkVehicle(final Vehicle vehicle) throws ParkingNotPossibleException {
        if (this.isOccupied) {
            throw new ParkingNotPossibleException("Slot " + this.slotId + " is already occupied.");
        }
        return new ParkingSlot(this.slotId, this.isNearLift, true, vehicle);
    }

    public ParkingSlot unParkVehicle() throws UnparkingNotPossible {
        if (!this.isOccupied) {
            throw new UnparkingNotPossible("Slot " + this.slotId + " is already vacant.");
        }
        return new ParkingSlot(this.slotId, this.isNearLift, false, null);
    }

}
