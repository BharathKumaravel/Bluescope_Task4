package org.example.dbconfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.exception.DataException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class ConnectionClass {


    private ConnectionClass() {
    }

        private static final HikariDataSource DATA_SOURCE;

    static {
        try {

            Properties prop =new Properties();
            prop.load(new FileInputStream("liquibase.properties"));

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(prop.getProperty("driver"));
            config.setJdbcUrl(prop.getProperty("url"));
            config.setUsername(prop.getProperty("username"));
            config.setPassword(prop.getProperty("username"));

            config.setMaximumPoolSize(Integer.parseInt(prop.getProperty("Hikari.maximumPool")));




            DATA_SOURCE = new HikariDataSource(config);
        } catch (Exception e) {

            throw new DataException("DB not connected",e);
        }


    }
    public static DataSource getConnect(){
        return DATA_SOURCE;

    }

}
