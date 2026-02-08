package ReservationComponent.repository;

import ReservationComponent.entity.Reservation;
import DataAccessComponent.interfaces.IDatabase;
import ReservationComponent.interfaces.IReservationRepository;
import java.sql.*;

public class ReservationRepository implements IReservationRepository {
    private final IDatabase db;

    public ReservationRepository(IDatabase db) {
        this.db = db;
    }

    @Override
    public void create(int vId, int sId, int tId) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("INSERT INTO reservations (vehicle_id, parking_spot_id, tariff_id, start_time, status) VALUES (?, ?, ?, now(), 'Active')")) {
            st.setInt(1, vId);
            st.setInt(2, sId);
            st.setInt(3, tId);
            st.executeUpdate();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    @Override
    public Reservation findActiveByVehicle(int vId) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM reservations WHERE vehicle_id = ? AND status = 'Active'")) {
            st.setInt(1, vId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return new Reservation(rs.getInt("id"), rs.getInt("vehicle_id"), rs.getInt("parking_spot_id"), rs.getInt("tariff_id"), rs.getTimestamp("start_time"));
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return null;
    }

    @Override
    public void close(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("UPDATE reservations SET end_time = now(), status = 'Finished' WHERE id = ?")) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}