import org.example.domain.CombinedAllocationStrategy;
import org.example.ParkingLot;
import org.example.domain.ParkingSlot;
import org.example.exceptions.UnparkingNotPossible;
import org.example.model.Receipt;
import org.example.exceptions.ParkingNotPossibleException;
import org.example.vehicle.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingLotTest {

    private ParkingLot parkingLot;
    private CombinedAllocationStrategy slotAllocationStrategy;
    private Car car;

    private ParkingSlot availableSlot1;
    private ParkingSlot availableSlot2;
    private ParkingSlot availableSlot3;
    private ParkingSlot availableSlot4;
    private ParkingSlot parkedSlot1;
    private ParkingSlot parkedSlot2;

    @BeforeEach
    void setup() {
        slotAllocationStrategy = mock(CombinedAllocationStrategy.class);
        car = mock(Car.class);

        availableSlot1 = mock(ParkingSlot.class);
        availableSlot2 = mock(ParkingSlot.class);
        availableSlot3 = mock(ParkingSlot.class);
        availableSlot4 = mock(ParkingSlot.class);

        parkedSlot1 = mock(ParkingSlot.class);
        parkedSlot2 = mock(ParkingSlot.class);

        parkingLot = new ParkingLot(createMockAvailableSlots(), slotAllocationStrategy);
    }

    @Test
    public void shouldParkVehicleWhenSlotIsAvailable() throws ParkingNotPossibleException {
        when(slotAllocationStrategy.allocate(any(), any(), eq(false)))
                .thenReturn(List.of(availableSlot1, availableSlot2));
        when(car.getVehicleNo()).thenReturn("ABC123");
        when(car.getSlotsRequired()).thenReturn(2);

        Receipt receipt = parkingLot.parkVehicle(car, false);

        assertNotNull(receipt);
        assertEquals("ABC123", receipt.vehicleNo());
        assertEquals(2, receipt.slots().size());
        assertEquals(Set.of("1", "2"), extractSlotIds(receipt.slots()));

        verify(availableSlot1).parkVehicle(car);
        verify(availableSlot2).parkVehicle(car);
    }

    @Test
    public void shouldThrowExceptionWhenNoSlotsAvailable() {
        when(slotAllocationStrategy.allocate(any(), any(), eq(false))).thenReturn(List.of());
        when(car.getVehicleNo()).thenReturn("DEF456");
        when(car.getSlotsRequired()).thenReturn(2);

        ParkingNotPossibleException exception = assertThrows(
                ParkingNotPossibleException.class,
                () -> parkingLot.parkVehicle(car, false)
        );

        assertEquals("Unable to Park, message=SPACE_UNAVAILABLE", exception.getMessage());
        verifyNoInteractions(availableSlot1, availableSlot2);
    }

    @Test
    public void shouldUnParkVehicleSuccessfully() throws UnparkingNotPossible {
        parkingLot = new ParkingLot(createMockOccupiedSlots(), slotAllocationStrategy);
        Receipt receipt = new Receipt(List.of(parkedSlot1, parkedSlot2), "SOMECAR1", LocalDateTime.now(), null);

        Receipt updatedReceipt = parkingLot.unParkVehicle(receipt);

        assertNotNull(updatedReceipt);
        assertEquals(receipt.vehicleNo(), updatedReceipt.vehicleNo());
        assertEquals(receipt.slots(), updatedReceipt.slots());
        assertNotNull(updatedReceipt.unParkedAt());

        verify(parkedSlot1).unParkVehicle();
        verify(parkedSlot2).unParkVehicle();
    }

    @Test
    public void shouldThrowExceptionWhenVehicleNotFoundDuringUnPark() {
        parkingLot = new ParkingLot(createMockOccupiedSlots(), slotAllocationStrategy);
        Receipt receipt = new Receipt(
                List.of(parkedSlot1, parkedSlot2),
                "KA04MW1000",
                LocalDateTime.now(),
                null);

        UnparkingNotPossible exception = assertThrows(
                UnparkingNotPossible.class,
                () -> parkingLot.unParkVehicle(receipt)
        );

        assertEquals("UnParking Not Possible, message = VEHICLE_ABSENT", exception.getMessage());
        verify(parkedSlot1, never()).unParkVehicle();
        verify(parkedSlot2, never()).unParkVehicle();
    }

    private List<ParkingSlot> createMockAvailableSlots() {
        mockSlot(availableSlot1, "1", true, true);
        mockSlot(availableSlot2, "2", false, true);
        mockSlot(availableSlot3, "3", false, true);
        mockSlot(availableSlot4, "4", true, true);

        return List.of(availableSlot1, availableSlot2, availableSlot3, availableSlot4);
    }

    private List<ParkingSlot> createMockOccupiedSlots() {
        mockOccupiedSlot(parkedSlot1, "1", true, "SOMECAR1");
        mockOccupiedSlot(parkedSlot2, "2", false, "SOMECAR1");

        return List.of(parkedSlot1, parkedSlot2);
    }

    private void mockSlot(ParkingSlot slot, String slotId, boolean isNearLift, boolean canPark) {
        when(slot.getSlotId()).thenReturn(slotId);
        when(slot.isNearLift()).thenReturn(isNearLift);
        when(slot.canPark(any(Car.class))).thenReturn(canPark);
    }

    private void mockOccupiedSlot(ParkingSlot slot, String slotId, boolean isNearLift, String vehicleNo) {
        mockSlot(slot, slotId, isNearLift, false);
        when(slot.isOccupied()).thenReturn(true);
        when(slot.getVehicle()).thenReturn(new Car(vehicleNo));
    }

    private Set<String> extractSlotIds(List<ParkingSlot> slots) {
        return slots.stream().map(ParkingSlot::getSlotId).collect(Collectors.toSet());
    }
}
