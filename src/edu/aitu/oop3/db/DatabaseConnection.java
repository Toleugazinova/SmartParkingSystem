package edu.aitu.oop3.db;

import interfaces.IDatabase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection implements IDatabase {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.ycdxqgmrsyzfgojsbucb";
    private static final String PASSWORD = loadPassword();

    private static String loadPassword() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            String value = props.getProperty("DB_PASSWORD");
            if (value == null || value.isBlank()) {
                throw new RuntimeException("DB_PASSWORD is not set in config.properties");
            }
            return value;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load DB_PASSWORD from config.properties. Make sure the file exists.", e);
        }
    }

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
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