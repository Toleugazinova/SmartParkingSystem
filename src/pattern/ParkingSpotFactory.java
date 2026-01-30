package pattern;

import entity.*;

public class ParkingSpotFactory {
    public static ParkingSpot createSpot(int id, String number, boolean available, String type) {
        if (type != null && type.equalsIgnoreCase("VIP")) {
            return new VIPSpot(id, number, available);
        } else {
            return new StandardSpot(id, number, available);
        }
    }
}