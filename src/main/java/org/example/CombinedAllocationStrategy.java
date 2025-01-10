package org.example;

import org.example.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class CombinedAllocationStrategy implements SlotAllocationStrategy {
    @Override
    public List<ParkingSlot> allocate(Vehicle vehicle, List<ParkingSlot> parkingSlots, boolean isNearLift) {
        int requiredSize = vehicle.getSlotsRequired();

        for (int i = 0; i <= parkingSlots.size() - requiredSize; i++) {
            boolean allSlotsAvailable = true;

            for (int j = 0; j < requiredSize; j++) {
                ParkingSlot currentSlot = parkingSlots.get(i + j);
                if (currentSlot.isOccupied() || (isNearLift && !currentSlot.isNearLift())) {
                    allSlotsAvailable = false;
                    break;
                }
            }

            if (allSlotsAvailable) {
                List<ParkingSlot> allocatedSlots = new ArrayList<>();
                for (int j = 0; j < requiredSize; j++) {
                    allocatedSlots.add(parkingSlots.get(i + j));
                }
                return allocatedSlots;
            }
        }

        return null;
    }
}
