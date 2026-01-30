package repository;

import interfaces.IDatabase;
import entity.ParkingSpot;
import interfaces.IRepository;
import pattern.ParkingSpotFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpotRepository implements IRepository<ParkingSpot> {
    private final IDatabase db;

    public ParkingSpotRepository(IDatabase db) {
        this.db = db;
    }

    @Override
    public List<ParkingSpot> getAll() {
        List<ParkingSpot> spots = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM parking_spots")) {
            while (rs.next()) {
                // Использование FACTORY
                ParkingSpot spot = ParkingSpotFactory.createSpot(
                        rs.getInt("id"),
                        rs.getString("spot_number"),
                        rs.getBoolean("is_available"),
                        rs.getString("spot_type")
                );
                spots.add(spot);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return spots;
    }

    @Override
    public ParkingSpot findById(int id) { return null; } // Заглушка

    public void updateStatus(int id, boolean available) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("UPDATE parking_spots SET is_available = ? WHERE id = ?")) {
            st.setBoolean(1, available);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}