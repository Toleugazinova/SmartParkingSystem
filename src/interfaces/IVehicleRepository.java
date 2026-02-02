package interfaces;

import entity.Vehicle;

public interface IVehicleRepository {
    Vehicle findByPlate(String plate);
    int createVehicle(String plate, String type);
}