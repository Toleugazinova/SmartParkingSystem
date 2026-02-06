package component;

import entity.ListResult;
import entity.ParkingSpot;
import service.ParkingLotManager;

public class MonitoringComponent {
    private final ParkingLotManager lotManager;

    public MonitoringComponent(ParkingLotManager lotManager) {
        this.lotManager = lotManager;
    }

    public ListResult<ParkingSpot> getAvailableSpots() {
        return lotManager.getAvailableSpots();
    }
}