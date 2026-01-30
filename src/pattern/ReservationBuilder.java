package pattern;

import entity.Reservation;
import java.sql.Timestamp;

// PATTERN: Builder
public class ReservationBuilder {
    private int id;
    private int vehicleId;
    private int spotId;
    private int tariffId;
    private Timestamp startTime;

    public ReservationBuilder setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public ReservationBuilder setSpotId(int spotId) {
        this.spotId = spotId;
        return this;
    }

    public ReservationBuilder setTariffId(int tariffId) {
        this.tariffId = tariffId;
        return this;
    }

    public ReservationBuilder setStartTime(Timestamp startTime) {
        this.startTime = startTime;
        return this;
    }

    public Reservation build() {
        return new Reservation(0, vehicleId, spotId, tariffId, startTime);
    }
}