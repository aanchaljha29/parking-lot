package org.example.domain;

import org.example.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CombinedAllocationStrategyTest {

    private CombinedAllocationStrategy allocationStrategy;
    private Vehicle vehicle;
    private ParkingSlot slot1;
    private ParkingSlot slot2;
    private ParkingSlot slot3;
    private ParkingSlot slot4;

    @BeforeEach
    public void setUp() {
        allocationStrategy = new CombinedAllocationStrategy();

        vehicle = mock(Vehicle.class);

        slot1 = new ParkingSlot("1", false);
        slot2 = new ParkingSlot("2", false);
        slot3 = new ParkingSlot("3", true);
        slot4 = new ParkingSlot("4", false);
    }

    @Test
    public void shouldAllocateSingleSlot() {
        when(vehicle.getSlotsRequired()).thenReturn(1);
        List<ParkingSlot> availableSlots = Arrays.asList(slot1, slot2, slot3, slot4);

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, false);

        assertEquals(1, allocatedSlots.size());
        assertEquals("1", allocatedSlots.get(0).getSlotId());
    }

    @Test
    public void shouldAllocateMultipleContiguousSlots() {
        when(vehicle.getSlotsRequired()).thenReturn(2);
        List<ParkingSlot> availableSlots = Arrays.asList(slot1, slot2, slot3, slot4);

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, false);

        assertEquals(2, allocatedSlots.size());
        assertTrue(allocatedSlots.stream().map(ParkingSlot::getSlotId).anyMatch(id -> id.equals("1")));
        assertTrue(allocatedSlots.stream().map(ParkingSlot::getSlotId).anyMatch(id -> id.equals("2")));
    }

    @Test
    public void shouldReturnEmptyWhenNoContiguousSlotsAvailable() {
        when(vehicle.getSlotsRequired()).thenReturn(3);
        List<ParkingSlot> availableSlots = Arrays.asList(slot1, slot2, slot4);

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, false);

        assertTrue(allocatedSlots.isEmpty());
    }

    @Test
    public void shouldAllocateSlotsNearLift() {
        when(vehicle.getSlotsRequired()).thenReturn(1);
        List<ParkingSlot> availableSlots = Arrays.asList(slot1, slot2, slot3, slot4);

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, true);

        assertEquals(1, allocatedSlots.size());
        assertEquals("3", allocatedSlots.get(0).getSlotId());
        assertTrue(allocatedSlots.get(0).isNearLift());
    }

    @Test
    public void shouldReturnEmptyWhenAllSlotsOccupied() {
        when(vehicle.getSlotsRequired()).thenReturn(1);
        List<ParkingSlot> availableSlots = Arrays.asList(
                new ParkingSlot("1", true, true, vehicle),
                new ParkingSlot("2", true, true, vehicle),
                new ParkingSlot("3", true, true, vehicle),
                new ParkingSlot("4", true, true, vehicle)
        );

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, false);

        assertTrue(allocatedSlots.isEmpty());
    }

    @Test
    public void shouldReturnEmptyWhenAvailableSlotsAreEmpty() {
        when(vehicle.getSlotsRequired()).thenReturn(2);
        List<ParkingSlot> availableSlots = List.of();

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, false);

        assertTrue(allocatedSlots.isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenAllocatingMoreSlotsThanAvailable() {
        when(vehicle.getSlotsRequired()).thenReturn(5);
        List<ParkingSlot> availableSlots = Arrays.asList(slot1, slot2, slot3);

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, false);

        assertTrue(allocatedSlots.isEmpty());
    }

    @Test
    public void shouldReturnEmptyWhenAllocatingNonContiguousSlots() {
        when(vehicle.getSlotsRequired()).thenReturn(2);
        List<ParkingSlot> availableSlots = Arrays.asList(slot1, slot3, slot4);

        List<ParkingSlot> allocatedSlots = allocationStrategy.allocate(vehicle, availableSlots, false);

        assertTrue(allocatedSlots.isEmpty());
    }
}
