package component;

import exception.InvalidVehiclePlateException;
import exception.NoFreeSpotsException;
import exception.ReservationException;
import service.ReservationService;

public class ReservationComponent {
    private final ReservationService reservationService;

    public ReservationComponent(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public String parkVehicle(String spotNumber, String plate, String type)
            throws ReservationException, InvalidVehiclePlateException, NoFreeSpotsException {
        return reservationService.parkVehicle(spotNumber, plate, type);
    }
}