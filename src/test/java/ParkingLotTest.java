import org.example.ParkingLot;
import org.example.exceptions.UnparkingNotPossible;
import org.example.exceptions.ParkingNotPossibleException;
import org.example.vehicle.Bike;
import org.example.vehicle.Car;
import org.example.vehicle.Minibus;
import org.example.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {
    private ParkingLot parkingLot;
    private Vehicle car;
    private Vehicle miniBus;
    private Vehicle bike;
    private List<Vehicle> vehicles;

    @BeforeEach
    void setup() {
        car = new Car("12345");
        bike = new Bike("234");
        miniBus = new Minibus("3456");
        vehicles = new ArrayList<>();
        parkingLot = new ParkingLot(5, vehicles);
    }

    @Test
    void shouldBeAbleToParkAVehicle() throws ParkingNotPossibleException {
        double spaceAvailableAfterParking = parkingLot.parkVehicle(miniBus);

        assertEquals(3, spaceAvailableAfterParking);
        assertEquals(1, parkingLot.getVehicles().size());
    }

    @Test
    void shouldThrowExceptionWhenSpaceNotAvailableWhenParkingAVehicle() throws ParkingNotPossibleException {
        parkingLot = new ParkingLot(1, vehicles);
        double spaceAvailableAfterParking = parkingLot.parkVehicle(bike);

        assertEquals(spaceAvailableAfterParking, 0.5);
        assertThrows(ParkingNotPossibleException.class, () -> parkingLot.parkVehicle(car));
    }

    @Test
    void shouldThrowExceptionWhenVehicleAlreadyParked() throws ParkingNotPossibleException {
        parkingLot = new ParkingLot(1, vehicles);
        double spaceAvailableAfterParking = parkingLot.parkVehicle(bike);

        assertEquals(spaceAvailableAfterParking, 0.5);
        assertThrows(ParkingNotPossibleException.class, () -> parkingLot.parkVehicle(bike));
    }

    @Test
    void shouldBeAbleToUnParkAVehicle() throws UnparkingNotPossible, ParkingNotPossibleException {
        double spaceAvailableAfterParking = parkingLot.parkVehicle(car);
        assertEquals(4, spaceAvailableAfterParking);

        double spaceAvailableAfterUnParking = parkingLot.unParkVehicle(car);
        assertEquals(5, spaceAvailableAfterUnParking);
    }

    @Test
    void shouldThrowExceptionWhenTryingToUnParkAVehicleWhichIsNotPresent() {
        assertThrows(UnparkingNotPossible.class, () -> parkingLot.unParkVehicle(car));
    }
}
