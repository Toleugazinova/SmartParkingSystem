package Business.ReservationComponent;

import Domain.entities.ParkingSpot;
import Domain.entities.Vehicle;
import DataAccessComponent.repositories.ParkingSpotRepository;
import DataAccessComponent.repositories.ReservationRepository;
import DataAccessComponent.repositories.VehicleRepository;
import Domain.entities.Tariff;
import Domain.exceptions.ReservationException;
import DataAccessComponent.repositories.TariffRepository;
import Domain.exceptions.InvalidVehiclePlateException;
import Domain.exceptions.NoFreeSpotsException;

public class ReservationService {
    private final ParkingSpotRepository spotRepo;
    private final VehicleRepository vehicleRepo;
    private final TariffRepository tariffRepo;
    private final ReservationRepository resRepo;
    private final ParkingLotManager lotManager;


    public ReservationService(ParkingSpotRepository s, VehicleRepository v, TariffRepository t, ReservationRepository r) {
        this.spotRepo = s;
        this.vehicleRepo = v;
        this.tariffRepo = t;
        this.resRepo = r;
        this.lotManager = ParkingLotManager.getInstance(s);
    }

    public String parkVehicle(String spotNumber, String plate, String type)
            throws ReservationException, InvalidVehiclePlateException, NoFreeSpotsException {
        if (plate == null || plate.isBlank()) {
            throw new InvalidVehiclePlateException("invalid vehicle plate");
        }

        if (lotManager.getAvailableSpots().getTotal() == 0) {
            throw new NoFreeSpotsException("no free spots");
        }

        ParkingSpot selectedSpot = spotRepo.getAll().stream()
                .filter(s -> s.getSpotNumber().equals(spotNumber) && s.isAvailable())
                .findFirst()
                .orElseThrow(() -> new NoFreeSpotsException("no free spots"));

        Tariff tariff = tariffRepo.getTariffBySpotType(selectedSpot.getSpotType());

        if (tariff == null) {
            throw new ReservationException("No tariff found for spot type: " + selectedSpot.getSpotType());
        }

        Vehicle vehicle = vehicleRepo.findByPlate(plate);
        if (vehicle != null && resRepo.findActiveByVehicle(vehicle.getId()) != null) {
            throw new ReservationException("reservation already active or expired");
        }
        int vehicleId = (vehicle == null) ? vehicleRepo.createVehicle(plate, type) : vehicle.getId();

        resRepo.create(vehicleId, selectedSpot.getId(), tariff.getId());

        spotRepo.updateStatus(selectedSpot.getId(), false);

        return "Vehicle parked at spot №" + spotNumber;
    }
}