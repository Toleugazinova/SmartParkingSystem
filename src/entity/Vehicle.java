package entity;

public class Vehicle {
    private int id;
    private String plateNumber;
    private String vehicleType;

    public Vehicle(int id, String plateNumber, String vehicleType) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
    }

    public int getId() { return id; }
    public String getPlateNumber() { return plateNumber; }
}