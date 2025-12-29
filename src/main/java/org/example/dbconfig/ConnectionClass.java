package org.example.dbconfig;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionClass {

    private static final String URL = "jdbc:mysql://localhost:3306/BankBranchManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    Logger LOG =LoggerFactory.getLogger(ConnectionClass.class);
    private ConnectionClass() {
        throw new UnsupportedOperationException("Utility class");
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
          return null;
        }
    }

}
