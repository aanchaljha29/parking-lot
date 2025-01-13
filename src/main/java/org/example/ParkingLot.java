package org.example;

import org.example.domain.ParkingSlot;
import org.example.domain.SlotAllocationStrategy;
import org.example.exceptions.ParkingNotPossibleException;
import org.example.exceptions.UnparkingNotPossible;
import org.example.model.ParkingError;
import org.example.model.Receipt;
import org.example.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot implements IParkingLot {

    //    private final String parkingLotId;
    private final List<ParkingSlot> parkingSlots;
    private final SlotAllocationStrategy slotAllocationStrategy;


    public ParkingLot(int availableSpace, SlotAllocationStrategy slotAllocationStrategy) {
        this.parkingSlots = IntStream.range(0, availableSpace)
                .mapToObj(slot -> new ParkingSlot(String.valueOf(slot + 1), slot % 5 == 0))
                .toList();
        this.slotAllocationStrategy = slotAllocationStrategy;
    }

    @Override
    public Receipt parkVehicle(Vehicle vehicle, boolean nearLift) throws ParkingNotPossibleException {
        List<ParkingSlot> allocatedSlots = slotAllocationStrategy.allocate(vehicle, parkingSlots, nearLift);
        if (allocatedSlots.isEmpty()) {
            throw new ParkingNotPossibleException("Unable to Park, message=" + ParkingError.SPACE_UNAVAILABLE);
        }
        allocatedSlots.forEach(parkingSlot -> parkingSlot.parkVehicle(vehicle));
        return new Receipt(allocatedSlots, vehicle.getVehicleNo(), LocalDateTime.now(), null);
    }

    @Override
    public Receipt unParkVehicle(Receipt receipt) throws UnparkingNotPossible {
        List<ParkingSlot> slotsToUnPark = findSlotsToUnPark(receipt);
        if (slotsToUnPark.isEmpty()) {
            throw new UnparkingNotPossible("UnParking Not Possible, message = " + ParkingError.VEHICLE_ABSENT);
        }
        slotsToUnPark.forEach(ParkingSlot::unParkVehicle);
        return new Receipt(receipt.slots(), receipt.vehicleNo(), receipt.parkedAt(), LocalDateTime.now());
    }

    private List<ParkingSlot> findSlotsToUnPark(Receipt receipt) {
        Set<String> receiptSlotIds = receipt.slots().stream()
                .map(ParkingSlot::getSlotId)
                .collect(Collectors.toSet());

        return parkingSlots.stream()
                .filter(slot -> receiptSlotIds.contains(slot.getSlotId())
                        && slot.isOccupied()
                        && slot.getVehicle().getVehicleNo().equals(receipt.vehicleNo()))
                .toList();
    }

    public ParkingLot(final List<ParkingSlot> parkingSlots, final SlotAllocationStrategy slotAllocationStrategy) {
        this.slotAllocationStrategy = slotAllocationStrategy;
        this.parkingSlots = parkingSlots;
    }
}
