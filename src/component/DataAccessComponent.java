package component;

import interfaces.IDatabase;
import repository.ParkingSpotRepository;
import repository.ReservationRepository;
import repository.TariffRepository;
import repository.VehicleRepository;

public class DataAccessComponent {
    private final IDatabase database;
    private final ParkingSpotRepository spotRepository;
    private final TariffRepository tariffRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;

    public DataAccessComponent(IDatabase database) {
        this.database = database;
        this.spotRepository = new ParkingSpotRepository(database);
        this.tariffRepository = new TariffRepository(database);
        this.vehicleRepository = new VehicleRepository(database);
        this.reservationRepository = new ReservationRepository(database);
    }

    public IDatabase getDatabase() {
        return database;
    }

    public ParkingSpotRepository getSpotRepository() {
        return spotRepository;
    }

    public TariffRepository getTariffRepository() {
        return tariffRepository;
    }

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public ReservationRepository getReservationRepository() {
        return reservationRepository;
    }
}