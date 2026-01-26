package repository;

import interfaces.IDatabase;
import entity.ParkingSpot;
import java.sql.*;

public class ParkingSpotRepository {
    private final IDatabase db;

    public ParkingSpotRepository(IDatabase db) {
        this.db = db;
    }

    public void printFreeSpots() {
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM parking_spots WHERE is_available = true")) {
            while (rs.next()) {
                System.out.println("Spot #" + rs.getString("spot_number") + " (" + rs.getString("spot_type") + ")");
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public ParkingSpot findBySpotNumber(String spotNumber) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM parking_spots WHERE spot_number = ?")) {
            st.setString(1, spotNumber);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new ParkingSpot(
                        rs.getInt("id"),
                        rs.getString("spot_number"),
                        rs.getBoolean("is_available"),
                        rs.getString("spot_type")
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateStatus(int id, boolean available) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("UPDATE parking_spots SET is_available = ? WHERE id = ?")) {
            st.setBoolean(1, available);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}