package org.example.vehicle;

import org.example.Constants;

public class Car extends Vehicle {
    public Car(String vehicleNo) {
        super(vehicleNo,  Constants.SLOTS_REQUIRED_FOR_CAR);
    }
}
