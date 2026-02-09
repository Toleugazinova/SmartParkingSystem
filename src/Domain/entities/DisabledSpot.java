package Domain.entities;

public class DisabledSpot extends ParkingSpot {
    public DisabledSpot(int id, String number, boolean available) {
        super(id, number, available, "disabled");
    }
}
