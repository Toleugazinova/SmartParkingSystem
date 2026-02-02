package service;

import entity.*;
import exception.ReservationException;
import pattern.ReservationBuilder;
import repository.*;
import java.sql.Timestamp;
import java.util.List;

public class ReservationService {
    private final ParkingSpotRepository spotRepo;
    private final VehicleRepository vehicleRepo;
    private final TariffRepository tariffRepo;
    private final ReservationRepository resRepo;

    public ReservationService(ParkingSpotRepository s, VehicleRepository v, TariffRepository t, ReservationRepository r) {
        this.spotRepo = s;
        this.vehicleRepo = v;
        this.tariffRepo = t;
        this.resRepo = r;
    }

    public String parkVehicle(String spotNumber, String plate, String type) throws ReservationException {
        if (plate == null || plate.isEmpty()) {
            throw new ReservationException("Plate number cannot be empty");
        }

        ParkingSpot selectedSpot = spotRepo.getAll().stream()
                .filter(s -> s.getSpotNumber().equals(spotNumber) && s.isAvailable())
                .findFirst()
                .orElseThrow(() -> new ReservationException("Spot #" + spotNumber + " is not available or does not exist"));

        Tariff tariff = tariffRepo.getTariffBySpotType(selectedSpot.getSpotType());

        if (tariff == null) {
            throw new ReservationException("No tariff found for spot type: " + selectedSpot.getSpotType());
        }

        Vehicle vehicle = vehicleRepo.findByPlate(plate);
        int vehicleId = (vehicle == null) ? vehicleRepo.createVehicle(plate, type) : vehicle.getId();

        resRepo.create(vehicleId, selectedSpot.getId(), tariff.getId());

        spotRepo.updateStatus(selectedSpot.getId(), false);

        return "Vehicle parked at spot №" + spotNumber;
    }
}