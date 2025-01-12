package org.example.domain;

import org.example.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CombinedAllocationStrategy implements SlotAllocationStrategy {

    @Override
    public List<ParkingSlot> allocate(Vehicle vehicle, List<ParkingSlot> availableSlots, boolean nearLift) {
        int requiredSlots = vehicle.getSlotsRequired();
        List<ParkingSlot> filteredSlots = filterSlots(availableSlots, nearLift, vehicle);
        return findContiguousSlots(filteredSlots, requiredSlots).orElseGet(ArrayList::new);
    }

    private List<ParkingSlot> filterSlots(List<ParkingSlot> slots, boolean nearLift, Vehicle vehicle) {
        return slots.stream()
                .filter(slot -> slot.canPark(vehicle))
                .filter(slot -> nearLift == slot.isNearLift())
                .collect(Collectors.toList());
    }

    private Optional<List<ParkingSlot>> findContiguousSlots(List<ParkingSlot> slots, int requiredSlots) {
        return IntStream.range(0, slots.size() - requiredSlots + 1)
                .mapToObj(slotId -> slots.subList(slotId, slotId + requiredSlots))
                .filter(this::areSlotsContiguous)
                .findFirst();
    }

    private boolean areSlotsContiguous(List<ParkingSlot> slots) {
        return IntStream.range(1, slots.size())
                .allMatch(currentIndex -> {
                    String previousSlotId = slots.get(currentIndex - 1).getSlotId();
                    String currentSlotId = slots.get(currentIndex).getSlotId();
                    return Integer.parseInt(currentSlotId) == Integer.parseInt(previousSlotId) + 1;
                });
    }

}
