package interfaces;

import java.sql.Connection;

public interface IDatabase {
    Connection getConnection();
}