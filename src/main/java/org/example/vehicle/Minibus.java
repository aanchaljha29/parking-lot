package org.example.vehicle;

import org.example.Constants;

public class Minibus extends Vehicle {
    public Minibus(String vehicleNo) {
        super(vehicleNo,  Constants.SLOTS_REQUIRED_FOR_MINIBUS);
    }
}
