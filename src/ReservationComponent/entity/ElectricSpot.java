package ReservationComponent.entity;

public class ElectricSpot extends ParkingSpot {
    public ElectricSpot(int id, String number, boolean available) {
        super(id, number, available, "electric");
    }
}
