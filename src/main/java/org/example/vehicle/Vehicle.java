package org.example.vehicle;

public class Vehicle {

    private final String vehicleNo;

    public Vehicle(String vehicleNo, double slotsRequired) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }
}
