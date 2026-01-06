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
            prop.load(new FileInputStream("C:\\Users\\BLUE SCOPE\\IdeaProjects\\BankBranchManagement\\BankBranchManagement\\src\\main\\resources\\db.properties"));


            Class.forName(prop.getProperty("db.driver"));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(prop.getProperty("db.URL"));
            config.setUsername(prop.getProperty("db.USER"));
            config.setPassword(prop.getProperty("db.PASSWORD"));

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
