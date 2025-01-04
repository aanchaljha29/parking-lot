package org.example.vehicle;

public class Vehicle {

    private final String vehicleNo;

    private final double slotsRequired;

    public Vehicle(String vehicleNo, double slotsRequired) {
        this.vehicleNo = vehicleNo;
        this.slotsRequired = slotsRequired;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public double getSlotsRequired() {
        return slotsRequired;
    }
}
