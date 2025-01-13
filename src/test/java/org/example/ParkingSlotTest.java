package org.example;

import org.example.domain.ParkingSlot;
import org.example.vehicle.Bike;
import org.example.vehicle.Car;
import org.example.vehicle.Minibus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSlotTest {

    private ParkingSlot parkingSlot;
    private Car car;

    @BeforeEach
    void setUp() {
        parkingSlot = new ParkingSlot("SLOT-001", true);
        car = new Car("KA-01-1234");
    }

    @Test
    void shouldHaveInitialVacantState() {
        assertFalse(parkingSlot.isOccupied());
        assertTrue(parkingSlot.isNearLift());
        assertNull(parkingSlot.getVehicle());
    }

    @Test
    void shouldParkVehicleSuccessfullyAndReturnUpdatedSlot() {
        ParkingSlot updatedSlot = parkingSlot.parkVehicle(car);

        assertTrue(updatedSlot.isOccupied());
        assertEquals(car, updatedSlot.getVehicle());
        assertNotEquals(parkingSlot, updatedSlot);
    }

    @Test
    void shouldUnParkVehicleSuccessfullyAndReturnUpdatedSlot() {
        ParkingSlot occupiedSlot = parkingSlot.parkVehicle(car);
        ParkingSlot vacantSlot = occupiedSlot.unParkVehicle();

        assertFalse(vacantSlot.isOccupied());
        assertNull(vacantSlot.getVehicle());
        assertNotEquals(occupiedSlot, vacantSlot);
    }

    @Test
    void shouldAllowParkingWhenSlotIsAvailable() {
        assertTrue(parkingSlot.canPark(car));
    }

    @Test
    void shouldNotAllowParkingWhenSlotIsOccupied() {
        ParkingSlot occupiedSlot = parkingSlot.parkVehicle(car);

        assertFalse(occupiedSlot.canPark(new Car("CAR-002")));
    }

    @Test
    void shouldNotAllowParkingWhenSameVehicleIsAlreadyParked() {
        ParkingSlot occupiedSlot = parkingSlot.parkVehicle(car);
        assertFalse(occupiedSlot.canPark(car));
    }

    @Test
    void shouldReturnTrueWhenVehicleIsAlreadyParked() {
        ParkingSlot occupiedSlot = parkingSlot.parkVehicle(car);
        assertTrue(occupiedSlot.isVehicleAlreadyParked(car));
    }

    @Test
    void shouldReturnFalseWhenVehicleIsNotParked() {
        assertFalse(parkingSlot.isVehicleAlreadyParked(car));
    }

    @Test
    void shouldMaintainSlotImmutability() {
        ParkingSlot updatedSlot = parkingSlot.parkVehicle(car);

        assertFalse(parkingSlot.isOccupied());
        assertNull(parkingSlot.getVehicle());

        assertTrue(updatedSlot.isOccupied());
        assertEquals(car, updatedSlot.getVehicle());
    }

    @Test
    void shouldHandleMultipleParkingAndUnParkingOperations() {
        ParkingSlot updatedSlot = parkingSlot.parkVehicle(car);
        assertTrue(updatedSlot.isOccupied());
        assertEquals(car, updatedSlot.getVehicle());

        ParkingSlot vacantSlot = updatedSlot.unParkVehicle();
        assertFalse(vacantSlot.isOccupied());
        assertNull(vacantSlot.getVehicle());

        ParkingSlot reParkedSlot = vacantSlot.parkVehicle(new Bike("KA-03-9999"));
        assertTrue(reParkedSlot.isOccupied());
        assertEquals("KA-03-9999", reParkedSlot.getVehicle().getVehicleNo());
    }

    @Test
    void shouldParkDifferentVehicleTypes() {
        Bike bike = new Bike("KA-02-1111");
        Minibus minibus = new Minibus("KA-04-2222");

        ParkingSlot bikeSlot = parkingSlot.parkVehicle(bike);
        assertEquals("KA-02-1111", bikeSlot.getVehicle().getVehicleNo());

        ParkingSlot minibusSlot = bikeSlot.unParkVehicle().parkVehicle(minibus);
        assertEquals("KA-04-2222", minibusSlot.getVehicle().getVehicleNo());
    }

}
