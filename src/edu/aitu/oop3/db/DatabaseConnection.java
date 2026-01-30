package edu.aitu.oop3.db;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection implements IDatabase {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            String url = "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres";
            String user = "postgres.ycdxqgmrsyzfgojsbucb";
            String password = "Ne0DvSdLpK4URYNQ";
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}