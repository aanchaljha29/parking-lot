package org.example.vehicle;

import org.example.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Separate the tests if required, when code grows and subclasses get more features
class VehicleTest {

    private static final String MINIBUS_VEHICLE_NUMBER = "MINI456";
    private static final String CAR_VEHICLE_NUMBER = "CAR789";
    private static final String BIKE_VEHICLE_NUMBER = "BIKE321";

    @Test
    void shouldReturnVehicleNumberAndSlotsForMinibusWhenMinibusIsCreated() {
        Minibus minibus = new Minibus(MINIBUS_VEHICLE_NUMBER);

        assertEquals(MINIBUS_VEHICLE_NUMBER, minibus.getVehicleNo());
        assertEquals(Constants.SLOTS_REQUIRED_FOR_MINIBUS, minibus.getSlotsRequired());
    }

    @Test
    void shouldReturnVehicleNumberAndSlotsForCarWhenCarIsCreated() {
        Vehicle car = new Vehicle(CAR_VEHICLE_NUMBER, Constants.SLOTS_REQUIRED_FOR_CAR);
        
        assertEquals(CAR_VEHICLE_NUMBER, car.getVehicleNo());
        assertEquals(Constants.SLOTS_REQUIRED_FOR_CAR, car.getSlotsRequired());
    }

    @Test
    void shouldReturnVehicleNumberAndSlotsForBikeWhenBikeIsCreated() {
        Vehicle bike = new Vehicle(BIKE_VEHICLE_NUMBER, Constants.SLOTS_REQUIRED_FOR_BIKE);
        
        assertEquals(BIKE_VEHICLE_NUMBER, bike.getVehicleNo());
        assertEquals(Constants.SLOTS_REQUIRED_FOR_BIKE, bike.getSlotsRequired());
    }
}
