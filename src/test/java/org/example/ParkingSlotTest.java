package org.example;

import org.example.domain.ParkingSlot;
import org.example.exceptions.ParkingNotPossibleException;
import org.example.exceptions.UnparkingNotPossible;
import org.example.vehicle.Bike;
import org.example.vehicle.Car;
import org.example.vehicle.Minibus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSlotTest {

    private ParkingSlot parkingSlot;
    private Car vehicle;

    @BeforeEach
    void setUp() {
        parkingSlot = new ParkingSlot("SLOT-001", true);
        vehicle = new Car("KA-01-1234");
    }

    @Test
    void shouldHaveInitialVacantState() {
        assertFalse(parkingSlot.isOccupied());
        assertTrue(parkingSlot.isNearLift());
        assertNull(parkingSlot.getVehicle());
    }

    @Test
    void shouldParkVehicleSuccessfully() throws ParkingNotPossibleException {
        ParkingSlot updatedSlot = parkingSlot.parkVehicle(vehicle);

        assertTrue(updatedSlot.isOccupied());
        assertEquals(vehicle, updatedSlot.getVehicle());
        assertNotEquals(parkingSlot, updatedSlot);
    }

    @Test
    void shouldThrowExceptionWhenParkingOnOccupiedSlot() throws ParkingNotPossibleException {
        ParkingSlot updatedSlot = parkingSlot.parkVehicle(vehicle);

        ParkingNotPossibleException exception = assertThrows(ParkingNotPossibleException.class,
                () -> updatedSlot.parkVehicle(new Car("KA-02-5678")));

        assertEquals("Slot SLOT-001 is already occupied.", exception.getMessage());
    }

    @Test
    void shouldUnParkVehicleSuccessfully() throws ParkingNotPossibleException, UnparkingNotPossible {
        ParkingSlot occupiedSlot = parkingSlot.parkVehicle(vehicle);
        ParkingSlot vacantSlot = occupiedSlot.unParkVehicle();

        assertFalse(vacantSlot.isOccupied());
        assertNull(vacantSlot.getVehicle());
        assertNotEquals(occupiedSlot, vacantSlot);
    }

    @Test
    void shouldThrowExceptionWhenUnParkingFromVacantSlot() {
        UnparkingNotPossible exception = assertThrows(UnparkingNotPossible.class, () -> {
            parkingSlot.unParkVehicle();
        });

        assertEquals("Slot SLOT-001 is already vacant.", exception.getMessage());
    }

    @Test
    void shouldHandleMultipleParkingAndUnParkingOperations() throws ParkingNotPossibleException, UnparkingNotPossible {
        ParkingSlot updatedSlot = parkingSlot.parkVehicle(vehicle);
        assertTrue(updatedSlot.isOccupied());
        assertEquals(vehicle, updatedSlot.getVehicle());

        ParkingSlot vacantSlot = updatedSlot.unParkVehicle();
        assertFalse(vacantSlot.isOccupied());
        assertNull(vacantSlot.getVehicle());

        ParkingSlot reParkedSlot = vacantSlot.parkVehicle(new Bike("KA-03-9999"));
        assertTrue(reParkedSlot.isOccupied());
        assertEquals("KA-03-9999", reParkedSlot.getVehicle().getVehicleNo());
    }

    @Test
    void shouldMaintainSlotImmutability() throws ParkingNotPossibleException {
        ParkingSlot updatedSlot = parkingSlot.parkVehicle(vehicle);

        assertFalse(parkingSlot.isOccupied());
        assertNull(parkingSlot.getVehicle());

        assertTrue(updatedSlot.isOccupied());
        assertEquals(vehicle, updatedSlot.getVehicle());
    }

    @Test
    void shouldParkDifferentVehicleTypes() throws ParkingNotPossibleException, UnparkingNotPossible {
        Bike bike = new Bike("KA-02-1111");
        Minibus minibus = new Minibus("KA-04-2222");

        ParkingSlot bikeSlot = parkingSlot.parkVehicle(bike);
        assertEquals("KA-02-1111", bikeSlot.getVehicle().getVehicleNo());

        ParkingSlot minibusSlot = bikeSlot.unParkVehicle().parkVehicle(minibus);
        assertEquals("KA-04-2222", minibusSlot.getVehicle().getVehicleNo());
    }

}