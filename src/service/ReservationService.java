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

    public String parkVehicle(String plate, String type) throws NoFreeSpotsException {
        Vehicle v = vehicleRepo.findByPlate(plate);
        int vId = (v == null) ? vehicleRepo.createVehicle(plate, type) : v.getId();
        ParkingSpot s = spotRepo.findFirstAvailable();
        if (s == null) throw new NoFreeSpotsException("No free spots!");
        Tariff t = tariffRepo.getTariffBySpotType(s.getSpotType());
        resRepo.create(vId, s.getId(), t.getId());
        spotRepo.updateStatus(s.getId(), false);
        return "Parked at " + s.getSpotNumber();
    }
}