package org.example.dbconfig;

import org.example.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.security.DigestException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionClass {

    private static final String URL = "jdbc:mysql://localhost:3306/BankBranchManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionClass() {
        throw new UnsupportedOperationException("Utility class");
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            throw new DataException("Driver not found",e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {

            throw new DataException("DB not connected",e);
        }
    }

}
