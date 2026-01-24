package entity;

public class Tariff {
    private int id;
    private String spotType;
    private double pricePerHour;

    public Tariff(int id, String spotType, double pricePerHour) {
        this.id = id;
        this.spotType = spotType;
        this.pricePerHour = pricePerHour;
    }

    public int getId() { return id; }
    public String getSpotType() { return spotType; }
    public double getPricePerHour() { return pricePerHour; }
}