package org.example.dbconfig;

import org.example.exception.DataException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class ConnectionClass {


    private ConnectionClass() {
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
            Properties prop =new Properties();
            InputStream inputStream =Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("db.properties") ;
            prop.load(inputStream);
            String URL=prop.getProperty("db.URL");
            String USER=prop.getProperty("db.USER");
            String PASSWORD=prop.getProperty("db.PASSWORD");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {

            throw new DataException("DB not connected",e);
        }
    }

}
