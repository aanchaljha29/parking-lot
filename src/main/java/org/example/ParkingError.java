package org.example;

import java.util.Objects;

public class ParkingError {

    private final boolean isPossible;
    private final ParkingStatus status;

    public ParkingError(boolean isPossible, ParkingStatus parkingStatus) {
        this.isPossible = isPossible;
        this.status = parkingStatus;
    }

    public boolean isPossible() {
        return isPossible;
    }

    public String getStatus() {
        return status.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingError that = (ParkingError) o;
        return isPossible == that.isPossible && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPossible, status);
    }

    @Override
    public String toString() {
        return "ParkingError{" +
                "isPossible=" + isPossible +
                ", status=" + status +
                '}';
    }
}
