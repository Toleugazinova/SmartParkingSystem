package edu.aitu.oop3.db;

import interfaces.IDatabase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresDB implements IDatabase {
    private static final String URL =
            "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER =
            "postgres.ycdxqgmrsyzfgojsbucb";
    private static final String PASSWORD = loadPassword();

    private PostgresDB() {
    }

    private static class Holder {
        private static final PostgresDB INSTANCE = new PostgresDB();
    }

    public static PostgresDB getInstance() {
        return Holder.INSTANCE;
    }

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
            throw new RuntimeException("Cannot load DB_PASSWORD from config.properties", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}