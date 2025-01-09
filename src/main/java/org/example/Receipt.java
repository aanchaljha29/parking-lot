package org.example;

import java.time.LocalDateTime;

public record Receipt(int slotNo, String vehicleNo, LocalDateTime parkedAt, LocalDateTime unParkedAt) {

}
