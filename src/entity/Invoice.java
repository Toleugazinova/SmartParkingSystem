package entity;

import java.sql.Timestamp;

public class Invoice {
    private final String plateNumber;
    private final int hours;
    private final double totalAmount;
    private final Timestamp startTime;
    private final Timestamp endTime;

    public Invoice(String plateNumber, int hours, double totalAmount, Timestamp startTime, Timestamp endTime) {
        this.plateNumber = plateNumber;
        this.hours = hours;
        this.totalAmount = totalAmount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public int getHours() {
        return hours;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Invoice{plate='" + plateNumber + "', hours=" + hours + ", total=" + totalAmount + " KZT}";
    }
}