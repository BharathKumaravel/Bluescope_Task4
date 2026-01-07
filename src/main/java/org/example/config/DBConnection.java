package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.exception.DataException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;


public final class DBConnection {



        private static final HikariDataSource DATA_SOURCE;

    static {
        try {

            Properties prop =new Properties();
            prop.load(new FileInputStream("C:\\Users\\BLUE SCOPE\\IdeaProjects\\BankBranchManagement\\BankBranchManagement\\liquibase.properties"));

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(prop.getProperty("driver"));
            config.setJdbcUrl(prop.getProperty("url"));
            config.setUsername(prop.getProperty("username"));
            config.setPassword(prop.getProperty("password"));

            config.setMaximumPoolSize(Integer.parseInt(prop.getProperty("Hikari.maximumPool")));




            DATA_SOURCE = new HikariDataSource(config);
        } catch (Exception e) {

            throw new DataException("DB not connected",e);
        }

    }

    private DBConnection() {
    }

    public static DataSource getConnect(){
        return DATA_SOURCE;

    }

}
