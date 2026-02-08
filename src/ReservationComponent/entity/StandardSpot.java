package ReservationComponent.entity;

public class StandardSpot extends ParkingSpot {
    public StandardSpot(int id, String number, boolean available) {
        super(id, number, available, "standard");
    }
}