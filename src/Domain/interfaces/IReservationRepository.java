package Domain.interfaces;

import Domain.entities.Reservation;

public interface IReservationRepository {
    void create(int vId, int sId, int tId);
    Reservation findActiveByVehicle(int vId);
    void close(int id);
}