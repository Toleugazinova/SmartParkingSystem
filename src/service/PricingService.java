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

    public String calculateAndPay(String plate, int hours)
            throws InvalidVehiclePlateException {

        Vehicle v = vehicleRepo.findByPlate(plate);
        if (v == null) throw new InvalidVehiclePlateException("Vehicle not found");

        Reservation r = resRepo.findActiveByVehicle(v.getId());
        if (r == null) return "No active session";

        double pricePerHour = tariffRepo.getPriceById(r.getTariffId());
        double total = hours * pricePerHour;

        resRepo.close(r.getId());
        spotRepo.updateStatus(r.getParkingSpotId(), true);

        return "Total: " + total + " KZT";
    }
}
