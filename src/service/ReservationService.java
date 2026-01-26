package service;

import entity.*;
import exception.NoFreeSpotsException;
import repository.*;

public class ReservationService {
    private final ParkingSpotRepository spotRepo;
    private final VehicleRepository vehicleRepo;
    private final TariffRepository tariffRepo;
    private final ReservationRepository resRepo;

    public ReservationService(ParkingSpotRepository s, VehicleRepository v, TariffRepository t, ReservationRepository r) {
        this.spotRepo = s; this.vehicleRepo = v; this.tariffRepo = t; this.resRepo = r;
    }

    public String parkVehicleAtSpot(String spotNumber, String type, String plate)
            throws NoFreeSpotsException {
        ParkingSpot s = spotRepo.findBySpotNumber(spotNumber);
        if (s == null || !s.isAvailable()) {
            throw new NoFreeSpotsException("This spot is not available!");
        }
        Vehicle v = vehicleRepo.findByPlate(plate);
        int vId = (v == null) ? vehicleRepo.createVehicle(plate, type) : v.getId();
        Tariff t = tariffRepo.getTariffBySpotType(s.getSpotType());
        resRepo.create(vId, s.getId(), t.getId());
        spotRepo.updateStatus(s.getId(), false);
        return "Parked at spot " + s.getSpotNumber();
    }
}