package interfaces;

import entity.Reservation;

public interface IReservationRepository {
    void create(int vId, int sId, int tId);
    Reservation findActiveByVehicle(int vId);
    void close(int id);
}