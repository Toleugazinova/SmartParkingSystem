package pattern;

import entity.Invoice;
import java.sql.Timestamp;

public class InvoiceBuilder {
    private String plateNumber;
    private int hours;
    private double totalAmount;
    private Timestamp startTime;
    private Timestamp endTime;

    public InvoiceBuilder setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public InvoiceBuilder setHours(int hours) {
        this.hours = hours;
        return this;
    }

    public InvoiceBuilder setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public InvoiceBuilder setStartTime(Timestamp startTime) {
        this.startTime = startTime;
        return this;
    }

    public InvoiceBuilder setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        return this;
    }

    public Invoice build() {
        return new Invoice(plateNumber, hours, totalAmount, startTime, endTime);
    }
}