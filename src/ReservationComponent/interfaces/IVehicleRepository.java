package ReservationComponent.interfaces;

import ReservationComponent.entity.Vehicle;

public interface IVehicleRepository {
    Vehicle findByPlate(String plate);
    int createVehicle(String plate, String type);
}