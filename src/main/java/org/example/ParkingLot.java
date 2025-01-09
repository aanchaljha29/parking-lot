package org.example;

import org.example.exceptions.ParkingNotPossibleException;
import org.example.exceptions.UnparkingNotPossible;
import org.example.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final List<ParkingSlot> parkingSlots;

    public ParkingLot(int availableSpace) {
        this.parkingSlots = new ArrayList<>();
        for (int slot = 0; slot < availableSpace; slot++) {
            boolean nearLift = slot % 5 == 0;
            parkingSlots.add(new ParkingSlot((int) (Math.random() * availableSpace), nearLift));
        }
    }

    public Receipt parkVehicle(Vehicle vehicle, boolean isNearLift) throws ParkingNotPossibleException {
        ParkingSlot slot = allocateSlot(parkingSlots, isNearLift);
        ParkingError parkingStatus = isParkingPossible(vehicle);
        if (parkingStatus.isPossible()) {
            throw new ParkingNotPossibleException(parkingStatus.getStatus());
        }
        slot.parkVehicle(vehicle);
        return new Receipt(slot.getSlotId(), vehicle.getVehicleNo(), LocalDateTime.now(), null);
    }

    public ParkingSlot allocateSlot(List<ParkingSlot> parkingSlots, boolean isNearLift) {
        return parkingSlots.stream()
                .filter(slot -> !slot.isOccupied() && (!isNearLift || slot.isNearLift()))
                .findFirst()
                .orElse(null);
    }

    private ParkingError isParkingPossible(Vehicle vehicle) {
        ParkingSlot slotWithSameVehicle = parkingSlots.stream()
                .filter(vehicleSlot -> vehicleSlot.getVehicle() != null
                        && vehicleSlot.getVehicle().equals(vehicle))
                .findFirst()
                .orElse(null);
        if (slotWithSameVehicle != null) {
            return new ParkingError(false, ParkingStatus.VEHICLE_PRESENT);
        }
        //TODO:Size take into account
//        if (slotWithSameVehicle == null) {
//            return new ParkingError(false, ParkingStatus.SPACE_UNAVAILABLE);
//        }
        return new ParkingError(true, ParkingStatus.POSSIBLE);
    }

    public Receipt unParkVehicle(Receipt receipt) throws UnparkingNotPossible {
        ParkingSlot slot = parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getSlotId() == receipt.slotNo()
                        && parkingSlot.getVehicle() != null
                        && parkingSlot.getVehicle().getVehicleNo().equals(receipt.vehicleNo()))
                .findFirst()
                .orElse(null);
        ParkingError unParkingStatus = isUnparkingPossible(slot);
        if (unParkingStatus.isPossible()) {
            throw new UnparkingNotPossible(unParkingStatus.getStatus());
        }
        if (slot != null) {
            slot.unParkVehicle();
        }
        return new Receipt(receipt.slotNo(), receipt.vehicleNo(), receipt.parkedAt(), LocalDateTime.now());
    }

    private ParkingError isUnparkingPossible(ParkingSlot slot) {
        if (slot == null) {
            return new ParkingError(false, ParkingStatus.VEHICLE_ABSENT);
        }
        return new ParkingError(true, ParkingStatus.POSSIBLE);
    }
}
