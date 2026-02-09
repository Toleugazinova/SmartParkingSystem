package DataAccessComponent.repositories;

import DataAccessComponent.interfaces.IDatabase;
import Domain.interfaces.ITariffRepository;
import Domain.entities.Tariff;
import java.sql.*;

public class TariffRepository implements ITariffRepository {
    private final IDatabase db;

    public TariffRepository(IDatabase db) {
        this.db = db;
    }

    @Override
    public void printAllTariffs() {
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tariffs")) {
            while (rs.next()) {
                System.out.println(rs.getString("spot_type") + ": " + rs.getDouble("price_per_hour") + " KZT/h");
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    @Override
    public Tariff getTariffBySpotType(String type) {
        String normalizedType = normalizeType(type);
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM tariffs WHERE LOWER(spot_type) = ?")) {
            st.setString(1, normalizedType);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Tariff(rs.getInt("id"), rs.getString("spot_type"), rs.getDouble("price_per_hour"));
            }

            if ("disabled".equals(normalizedType)) {
                return findStandardTariff(con);
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return null;
    }

    private Tariff findStandardTariff(Connection con) throws SQLException {
        try (PreparedStatement fallback = con.prepareStatement("SELECT * FROM tariffs WHERE LOWER(spot_type) = ?")) {
            fallback.setString(1, "standard");
            ResultSet rs = fallback.executeQuery();
            if (rs.next()) {
                return new Tariff(rs.getInt("id"), rs.getString("spot_type"), rs.getDouble("price_per_hour"));
            }
        }
        return null;
    }

    private String normalizeType(String type) {
        if (type == null || type.isBlank()) {
            return "standard";
        }
        return type.trim().toLowerCase();
    }

    @Override
    public double getPriceById(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT price_per_hour FROM tariffs WHERE id = ?")) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return 0;
    }
}
