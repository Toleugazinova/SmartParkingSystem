package DataAccessComponent.repositories;

import Domain.entities.ListResult;
import Domain.entities.ParkingSpot;
import DataAccessComponent.interfaces.IDatabase;
import Domain.interfaces.IParkingSpotRepository;
import Business.ReservationComponent.ParkingSpotFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpotRepository implements IParkingSpotRepository {
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
    public ListResult<ParkingSpot> findAvailable() {
        List<ParkingSpot> spots = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM parking_spots WHERE is_available = true")) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ParkingSpot spot = ParkingSpotFactory.createSpot(
                        rs.getInt("id"),
                        rs.getString("spot_number"),
                        rs.getBoolean("is_available"),
                        rs.getString("spot_type")
                );
                spots.add(spot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ListResult<>(spots);
    }

    @Override
    public ParkingSpot findById(int id) { return null; } // Заглушка

    @Override
    public void updateStatus(int id, boolean available) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("UPDATE parking_spots SET is_available = ? WHERE id = ?")) {
            st.setBoolean(1, available);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
