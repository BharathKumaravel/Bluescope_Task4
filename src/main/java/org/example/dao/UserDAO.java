package org.example.dao;

import org.example.config.DBConnection;
import org.example.exception.DataException;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final int INSERT_MAIL=1;
    private static final int INSERT_PASSWORD=2;

    private static final int GET_PASSWORD=1;

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

    static final String ADD_SQL="INSERT INTO users (email,password) values(?,?)";

    static final String GET_PASS="SELECT password from users where email = ?";


    public void addUser(User user) {
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps = con.prepareStatement(ADD_SQL)){

            ps.setString(INSERT_MAIL, user.getEmail());
            ps.setString(INSERT_PASSWORD, user.getPassword());

            ps.executeUpdate();
           LOG.info("done");
        } catch (SQLException e) {
            throw new DataException("Connection Failed",e);
        }
    }

    public String getPass(String mail) {
        try(Connection con = DBConnection.getConnect().getConnection();
        PreparedStatement ps = con.prepareStatement(GET_PASS)){
            ps.setString(GET_PASSWORD,mail);
            ResultSet rs = ps.executeQuery();
            LOG.info("Query Executed");
            if(rs.next()) {
                return rs.getString("password");
            } else {
                return null;
            }
        }catch (SQLException e) {
            throw new DataException("Failed to get Password from db",e);
        }
    }
}
