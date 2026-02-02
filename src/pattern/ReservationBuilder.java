package pattern;

import java.sql.Timestamp;

public class ReservationBuilder {
    private int id;
    private int vehicleId;
    private int spotId;
    private int tariffId;
    private Timestamp startTime;

    public ReservationBuilder(Builder builder){
        this.id=id;
        this.vehicleId = vehicleId;
        this.spotId = spotId;
        this.tariffId = tariffId;
        this.startTime = startTime;
    }
    public static class Builder {
        private int id;
        private int vehicleId;
        private int spotId;
        private int tariffId;
        private Timestamp startTime;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder vehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder spotId(int spotId) {
            this.spotId = spotId;
            return this;
        }

        public Builder tariffId(int tariffId) {
            this.tariffId = tariffId;
            return this;
        }

        public Builder startTime(Timestamp startTime) {
            this.startTime = startTime;
            return this;
        }

        public ReservationBuilder build() {
            return new ReservationBuilder(this);
        }
    }
    public int getId(){return id;}
    public int getVehicleId(){return vehicleId;}
    public int getSpotId(){return spotId;}
    public int getTariffId(){return tariffId;}
    public Timestamp getStartTime(){return startTime;}
}