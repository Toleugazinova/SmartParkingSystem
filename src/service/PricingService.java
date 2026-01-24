package service;

import entity.*;
import exception.InvalidVehiclePlateException;
import repository.*;

public class PricingService {
    private final ReservationRepository resRepo;
    private final TariffRepository tariffRepo;
    private final ParkingSpotRepository spotRepo;
    private final VehicleRepository vehicleRepo;

    public PricingService(ReservationRepository r, TariffRepository t, ParkingSpotRepository s, VehicleRepository v) {
        this.resRepo = r; this.tariffRepo = t; this.spotRepo = s; this.vehicleRepo = v;
    }

    public String calculateAndPay(String plate) throws InvalidVehiclePlateException {
        Vehicle v = vehicleRepo.findByPlate(plate);
        if (v == null) throw new InvalidVehiclePlateException("Vehicle not found");
        Reservation r = resRepo.findActiveByVehicle(v.getId());
        if (r == null) return "No active session";
        long hours = (long) Math.ceil((System.currentTimeMillis() - r.getStartTime().getTime()) / 3600000.0);
        double total = (hours == 0 ? 1 : hours) * tariffRepo.getPriceById(r.getTariffId());
        resRepo.close(r.getId());
        spotRepo.updateStatus(r.getParkingSpotId(), true);
        return "Fee: " + total + " KZT";
    }
}
