package interfaces;

import entity.ListResult;
import entity.ParkingSpot;
import java.util.List;

public interface IParkingSpotRepository extends IRepository<ParkingSpot> {
    @Override
    List<ParkingSpot> getAll();

    ListResult<ParkingSpot> findAvailable();

    void updateStatus(int id, boolean available);
}