package entity;

public class ParkingSpot {
    private int id;
    private String spotNumber;
    private boolean isAvailable;
    private String spotType;

    public ParkingSpot(int id, String spotNumber, boolean isAvailable, String spotType) {
        this.id = id;
        this.spotNumber = spotNumber;
        this.isAvailable = isAvailable;
        this.spotType = spotType;
    }

    public int getId() { return id; }
    public String getSpotNumber() { return spotNumber; }
    public String getSpotType() { return spotType; }
    public boolean isAvailable() { return isAvailable; }
}