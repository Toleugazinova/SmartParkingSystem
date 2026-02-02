package pattern;

import entity.*;

public class ParkingSpotFactory {
    public static ParkingSpot createSpot(int id, String number, boolean available, String type) {
        if (type == null) {
            return new StandardSpot(id, number, available);
        }

        if (type.equalsIgnoreCase("disabled")) {
            return new DisabledSpot(id, number, available);
        }

        if (type.equalsIgnoreCase("electric")) {
            return new ElectricSpot(id, number, available);
        }

        return new StandardSpot(id, number, available);
    }
}