package Domain.interfaces;

import Domain.entities.Vehicle;

public interface IVehicleRepository {
    Vehicle findByPlate(String plate);
    int createVehicle(String plate, String type);
}