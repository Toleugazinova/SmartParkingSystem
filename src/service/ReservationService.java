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

    public String parkVehicle(String plate, String type) throws ReservationException {
        if (plate == null || plate.isEmpty()) throw new ReservationException("Plate is empty");

        Vehicle v = vehicleRepo.findByPlate(plate);
        int vId = (v == null) ? vehicleRepo.createVehicle(plate, type) : v.getId();

        List<ParkingSpot> allSpots = spotRepo.getAll();
        ParkingSpot freeSpot = allSpots.stream()
                .filter(ParkingSpot::isAvailable)
                .findFirst()
                .orElseThrow(() -> new ReservationException("No free spots"));

        Tariff t = tariffRepo.getTariffBySpotType(freeSpot.getSpotType());

        Reservation res = new ReservationBuilder()
                .setVehicleId(vId)
                .setSpotId(freeSpot.getId())
                .setTariffId(t.getId())
                .setStartTime(new Timestamp(System.currentTimeMillis()))
                .build();

        resRepo.create(vId, freeSpot.getId(), t.getId());
        spotRepo.updateStatus(freeSpot.getId(), false);

        return "Parked at " + freeSpot.getSpotNumber();
    }
}