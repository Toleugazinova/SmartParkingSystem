package repository;

import interfaces.IDatabase;
import interfaces.IVehicleRepository;
import entity.Vehicle;
import java.sql.*;

public class VehicleRepository implements IVehicleRepository {
    private final IDatabase db;

    public VehicleRepository(IDatabase db) {
        this.db = db;
    }

    @Override
    public Vehicle findByPlate(String plate) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM vehicles WHERE plate_number = ?")) {
            st.setString(1, plate);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return new Vehicle(rs.getInt("id"), rs.getString("plate_number"), rs.getString("vehicle_type"));
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return null;
    }

    @Override
    public int createVehicle(String plate, String type) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("INSERT INTO vehicles (plate_number, vehicle_type) VALUES (?, ?) RETURNING id")) {
            st.setString(1, plate);
            st.setString(2, type);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return -1;
    }
}