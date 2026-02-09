package Domain.interfaces;

import DataAccessComponent.interfaces.IRepository;
import Domain.entities.ListResult;
import Domain.entities.ParkingSpot;
import java.util.List;

public interface IParkingSpotRepository extends IRepository<ParkingSpot> {
    @Override
    List<ParkingSpot> getAll();

    ListResult<ParkingSpot> findAvailable();

    void updateStatus(int id, boolean available);
}