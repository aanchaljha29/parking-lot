import org.example.CombinedAllocationStrategy;
import org.example.ParkingLot;
import org.example.ParkingSlot;
import org.example.Receipt;
import org.example.exceptions.UnparkingNotPossible;
import org.example.exceptions.ParkingNotPossibleException;
import org.example.vehicle.Bike;
import org.example.vehicle.Car;
import org.example.vehicle.Minibus;
import org.example.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {
    private ParkingLot parkingLot;
    private Vehicle car;
    private Vehicle miniBus;
    private Vehicle bike;

    @BeforeEach
    void setup() {
        car = new Car("12345");
        bike = new Bike("234");
        miniBus = new Minibus("3456");
        parkingLot = new ParkingLot(10, new CombinedAllocationStrategy());
    }

    @Test
    void shouldBeAbleToParkAVehicle() throws ParkingNotPossibleException {
        Receipt receipt = parkingLot.parkVehicle(miniBus, false);

        assertNotNull(receipt);
        assertEquals(receipt.vehicleNo(), miniBus.getVehicleNo());
    }


    @Test
    void shouldThrowExceptionWhenSpaceNotAvailableWhenParkingAVehicle() {
        parkingLot = new ParkingLot(1, new CombinedAllocationStrategy());

        assertThrows(ParkingNotPossibleException.class, () -> parkingLot.parkVehicle(car, true));
    }

    @Test
    void shouldThrowExceptionWhenVehicleAlreadyParked() throws ParkingNotPossibleException {
        parkingLot = new ParkingLot(2, new CombinedAllocationStrategy());
        Receipt receipt = parkingLot.parkVehicle(bike, false);

        assertNotNull(receipt);
        assertThrows(ParkingNotPossibleException.class, () -> parkingLot.parkVehicle(bike, true));
    }

    @Test
    void shouldBeAbleToUnParkAVehicle() throws UnparkingNotPossible, ParkingNotPossibleException {
        Receipt receiptBeforeParking = parkingLot.parkVehicle(car, false);
        assertNotNull(receiptBeforeParking);

        Receipt receiptAfterUnparking = parkingLot.unParkVehicle(receiptBeforeParking);
        assertNotNull(receiptAfterUnparking.unParkedAt());
    }

    @Test
    void shouldThrowExceptionWhenTryingToUnParkAVehicleWhichIsNotPresent() {
        Receipt fakeReceipt = new Receipt(List.of(new ParkingSlot("1", false)), "123", LocalDateTime.now(), null);

        assertThrows(UnparkingNotPossible.class, () -> parkingLot.unParkVehicle(fakeReceipt));
    }
}
