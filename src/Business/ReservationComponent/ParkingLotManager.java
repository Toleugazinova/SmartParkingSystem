package Business.ReservationComponent;

import Domain.entities.ListResult;
import Domain.entities.ParkingSpot;
import DataAccessComponent.repositories.ParkingSpotRepository;

public class ParkingLotManager {
    private static ParkingLotManager instance;
    private final ParkingSpotRepository spotRepository;

    private ParkingLotManager(ParkingSpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public static synchronized ParkingLotManager getInstance(ParkingSpotRepository spotRepository) {
        if (instance == null) {
            instance = new ParkingLotManager(spotRepository);
        }
        return instance;
    }

    public ListResult<ParkingSpot> getAvailableSpots() {
        return spotRepository.findAvailable();
    }
}