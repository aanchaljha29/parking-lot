package org.example.model;

import java.util.Objects;

public class ParkingStatus {
    private final boolean isPossible;
    private final ParkingError status;

    public ParkingStatus(boolean isPossible, ParkingError parkingStatus) {
        this.isPossible = isPossible;
        this.status = parkingStatus;
    }

    public boolean isNotPossible() {
        return !isPossible;
    }

    public String getStatus() {
        return status.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingStatus that = (ParkingStatus) o;
        return isPossible == that.isPossible && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPossible, status);
    }

    @Override
    public String toString() {
        return "ParkingError{" + "isPossible=" + isPossible + ", status=" + status + '}';
    }
}
