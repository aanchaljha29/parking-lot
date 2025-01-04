import org.example.ParkingLot;
import org.example.exceptions.SpaceNotAvailableException;
import org.example.exceptions.VehicleAlreadyParkedException;
import org.example.exceptions.VehicleNotFoundException;
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
    void shouldBeAbleToParkAVehicle() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        double spaceAvailableAfterParking = parkingLot.parkVehicle(miniBus);

        assertEquals(3, spaceAvailableAfterParking);
        assertEquals(1, parkingLot.getVehicles().size());
    }

    @Test
    void shouldThrowExceptionWhenSpaceNotAvailableWhenParkingAVehicle() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        parkingLot = new ParkingLot(1, vehicles);
        double spaceAvailableAfterParking = parkingLot.parkVehicle(bike);

        assertEquals(spaceAvailableAfterParking, 0.5);
        assertThrows(SpaceNotAvailableException.class, () -> parkingLot.parkVehicle(car));
    }

    @Test
    void shouldThrowExceptionWhenVehicleAlreadyParked() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        parkingLot = new ParkingLot(1, vehicles);
        double spaceAvailableAfterParking = parkingLot.parkVehicle(bike);

        assertEquals(spaceAvailableAfterParking, 0.5);
        assertThrows(VehicleAlreadyParkedException.class, () -> parkingLot.parkVehicle(bike));
    }

    @Test
    void shouldBeAbleToUnParkAVehicle() throws SpaceNotAvailableException, VehicleNotFoundException, VehicleAlreadyParkedException {
        double spaceAvailableAfterParking = parkingLot.parkVehicle(car);
        assertEquals(4, spaceAvailableAfterParking);

        double spaceAvailableAfterUnParking = parkingLot.unParkVehicle(car);
        assertEquals(5, spaceAvailableAfterUnParking);
    }

    @Test
    void shouldThrowExceptionWhenTryingToUnParkAVehicleWhichIsNotPresent() {
        assertThrows(VehicleNotFoundException.class, () -> parkingLot.unParkVehicle(car));
    }
}
