package Business.PaymentComponent.service;

import Domain.entities.Reservation;
import Domain.entities.Vehicle;
import Domain.exceptions.InvalidVehiclePlateException;
import DataAccessComponent.repositories.ParkingSpotRepository;
import DataAccessComponent.repositories.ReservationRepository;
import DataAccessComponent.repositories.VehicleRepository;
import DataAccessComponent.repositories.TariffRepository;
import Domain.exceptions.ReservationException;
import ReportingComponent.InvoiceBuilder;

public class PricingService {
    private final ReservationRepository resRepo;
    private final TariffRepository tariffRepo;
    private final ParkingSpotRepository spotRepo;
    private final VehicleRepository vehicleRepo;

    public PricingService(ReservationRepository r, TariffRepository t, ParkingSpotRepository s, VehicleRepository v) {
        this.resRepo = r;
        this.tariffRepo = t;
        this.spotRepo = s;
        this.vehicleRepo = v;
    }

    public String calculateAndPay(String plate, int hours)
            throws InvalidVehiclePlateException, ReservationException {

        Vehicle v = vehicleRepo.findByPlate(plate);
        if (v == null) throw new InvalidVehiclePlateException("invalid vehicle plate");

        Reservation r = resRepo.findActiveByVehicle(v.getId());
        if (r == null) throw new ReservationException("reservation already active or expired");

        double pricePerHour = tariffRepo.getPriceById(r.getTariffId());
        double total = hours * pricePerHour;

        resRepo.close(r.getId());
        spotRepo.updateStatus(r.getParkingSpotId(), true);


        return new InvoiceBuilder()
                .setPlateNumber(plate)
                .setHours(hours)
                .setTotalAmount(total)
                .setStartTime(r.getStartTime())
                .setEndTime(new java.sql.Timestamp(System.currentTimeMillis()))
                .build()
                .toString();
    }
}
