package org.example;

import org.example.vehicle.Vehicle;

import java.util.List;

public interface SlotAllocationStrategy {
    List<ParkingSlot> allocate(Vehicle vehicle, List<ParkingSlot> parkingSlots, boolean isNearLift);
}
