package repository;

import interfaces.IDatabase;
import entity.Tariff;
import java.sql.*;

public class TariffRepository {
    private final IDatabase db;

    public TariffRepository(IDatabase db) {
        this.db = db;
    }

    public void printAllTariffs() {
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tariffs")) {
            while (rs.next()) {
                System.out.println(rs.getString("spot_type") + ": " + rs.getDouble("price_per_hour") + " KZT/h");
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public Tariff getTariffBySpotType(String type) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM tariffs WHERE spot_type = ?")) {
            st.setString(1, type);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return new Tariff(rs.getInt("id"), rs.getString("spot_type"), rs.getDouble("price_per_hour"));
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return null;
    }

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
