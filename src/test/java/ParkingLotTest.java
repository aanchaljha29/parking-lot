import org.example.ParkingLot;
import org.example.SpaceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {
    private ParkingLot parkingLot;

    @BeforeEach
    void setup() {
        parkingLot = new ParkingLot(5);
    }

    @Test
    void shouldBeAbleToParkAVehicle() throws SpaceNotAvailableException {
        int spaceAvailableAfterParking = parkingLot.parkVehicle();

        assertEquals(4, spaceAvailableAfterParking);
    }

    @Test
    void shouldThrowExceptionWhenSpaceNotAvailableWhenParkingAVehicle() throws SpaceNotAvailableException {
        parkingLot = new ParkingLot(1);
        int spaceAvailableAfterParking = parkingLot.parkVehicle();

        assertEquals(spaceAvailableAfterParking, 0);
        assertThrows(SpaceNotAvailableException.class, () -> parkingLot.parkVehicle());
    }

    @Test
    void shouldBeAbleToUnParkAVehicle() throws SpaceNotAvailableException {
        int spaceAvailableAfterParking = parkingLot.parkVehicle();
        assertEquals(4, spaceAvailableAfterParking);

        int spaceAvailableAfterUnParking = parkingLot.unParkVehicle();
        assertEquals(5, spaceAvailableAfterUnParking);
    }
}
