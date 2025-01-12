package org.example;

import org.example.exceptions.ParkingNotPossibleException;
import org.example.exceptions.UnparkingNotPossible;
import org.example.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLot implements IParkingLot {
    private final List<ParkingSlot> parkingSlots;

    private final SlotAllocationStrategy slotAllocationStrategy;

    public ParkingLot(int availableSpace, SlotAllocationStrategy slotAllocationStrategy) {
        this.parkingSlots = new ArrayList<>();
        for (int slot = 0; slot < availableSpace; slot++) {
            boolean nearLift = slot % 5 == 0;
            parkingSlots.add(new ParkingSlot("1", nearLift));
        }
        this.slotAllocationStrategy = slotAllocationStrategy;
    }

    @Override
    public Receipt parkVehicle(Vehicle vehicle, boolean isNearLift) throws ParkingNotPossibleException {
        ParkingStatus parkingStatus = isParkingPossible(vehicle);
        if (parkingStatus.isNotPossible()) {
            throw new ParkingNotPossibleException(parkingStatus.getStatus());
        }
        List<ParkingSlot> allocatedSlots = slotAllocationStrategy.allocate(vehicle, parkingSlots, isNearLift);
        if (allocatedSlots == null) {
            throw new ParkingNotPossibleException(ParkingError.SPACE_UNAVAILABLE.toString());
        }
        for (ParkingSlot slot : allocatedSlots) {
            slot.parkVehicle(vehicle);
        }
        return new Receipt(allocatedSlots, vehicle.getVehicleNo(), LocalDateTime.now(), null);
    }

    private ParkingStatus isParkingPossible(Vehicle vehicle) {
        ParkingSlot slot = parkingSlots.stream()
                .filter(vehicleSlot -> vehicleSlot.isOccupied()
                        && vehicleSlot.getVehicle().equals(vehicle))
                .findFirst()
                .orElse(null);
        if (slot != null) {
            return new ParkingStatus(false, ParkingError.VEHICLE_PRESENT);
        }
        return new ParkingStatus(true, ParkingError.POSSIBLE);
    }

    @Override
    public Receipt unParkVehicle(Receipt receipt) throws UnparkingNotPossible {
        List<ParkingSlot> slotsToUnpark = parkingSlots.stream()
                .filter(slot -> receipt.slots().contains(slot)
                        && slot.isOccupied()
                        && slot.getVehicle().getVehicleNo().equals(receipt.vehicleNo()))
                .collect(Collectors.toList());
        ParkingStatus unParkingStatus = isUnparkingPossible(slotsToUnpark);
        if (unParkingStatus.isNotPossible()) {
            throw new UnparkingNotPossible(unParkingStatus.getStatus());
        }
        for (ParkingSlot slot : slotsToUnpark) {
            slot.unParkVehicle();
        }
        return new Receipt(receipt.slots(), receipt.vehicleNo(), receipt.parkedAt(), LocalDateTime.now());
    }

    private ParkingStatus isUnparkingPossible(List<ParkingSlot> slots) {
        if (slots == null || slots.isEmpty()) {
            return new ParkingStatus(false, ParkingError.VEHICLE_ABSENT);
        }
        return new ParkingStatus(true, ParkingError.POSSIBLE);
    }
}
