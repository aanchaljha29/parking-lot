package org.example.vehicle;

import org.example.Constants;

public class Bike extends Vehicle {
    public Bike(String vehicleNo) {
        super(vehicleNo, Constants.SLOTS_REQUIRED_FOR_BIKE);
    }
}
