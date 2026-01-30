package entity;

public class VIPSpot extends ParkingSpot {
    public VIPSpot(int id, String number, boolean available) {
        super(id, number, available, "VIP");
    }
}