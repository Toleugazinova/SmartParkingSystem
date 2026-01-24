package entity;

import java.sql.Timestamp;

public class Reservation {
    private int id;
    private int vehicleId;
    private int parkingSpotId;
    private int tariffId;
    private Timestamp startTime;

    public Reservation(int id, int vehicleId, int parkingSpotId, int tariffId, Timestamp startTime) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.parkingSpotId = parkingSpotId;
        this.tariffId = tariffId;
        this.startTime = startTime;
    }

    public int getId() { return id; }
    public int getParkingSpotId() { return parkingSpotId; }
    public int getTariffId() { return tariffId; }
    public Timestamp getStartTime() { return startTime; }
}