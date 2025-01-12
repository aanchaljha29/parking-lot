package org.example.model;

import org.example.domain.ParkingSlot;

import java.time.LocalDateTime;
import java.util.List;

public record Receipt(List<ParkingSlot> slots, String vehicleNo, LocalDateTime parkedAt, LocalDateTime unParkedAt) {

}
