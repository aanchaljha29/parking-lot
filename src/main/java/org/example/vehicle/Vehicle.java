package org.example.vehicle;

public class Vehicle {

    private final String vehicleNo;

    private  final int slotsRequired;

    public Vehicle(String vehicleNo, int slotsRequired1) {
        this.vehicleNo = vehicleNo;
        this.slotsRequired = slotsRequired1;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public int getSlotsRequired() {
        return slotsRequired;
    }
}
