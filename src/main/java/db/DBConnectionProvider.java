package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {
    private static DBConnectionProvider dbConnectionProvider = null;
    private static Connection connection = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eshop";
    private static final String DB_NAME = "root";
    private static final String DB_PASSWORD = "hilg456";

    private DBConnectionProvider() {
    }

    public static DBConnectionProvider getInstance() {
        if (dbConnectionProvider == null) {
            dbConnectionProvider = new DBConnectionProvider();
        }
        return dbConnectionProvider;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_NAME, DB_PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
