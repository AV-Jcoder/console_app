package org.afoninav.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcConnector {
    static Connection getConnection() {
        ResourceBundle bundle = ResourceBundle.getBundle("testdb");
        String url = bundle.getString("URL");
        String login = bundle.getString("LOGIN");
        String password = bundle.getString("PASSWORD");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
