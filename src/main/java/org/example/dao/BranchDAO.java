package org.example.dao;

import org.example.config.DBConnection;
import org.example.exception.DataException;
import org.example.model.Branch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {


    static final int INSERT_BRANCH_ID=1;
    static final int INSERT_BRANCH_NAME=2;
    static final int INSERT_BRANCH_IFSCCODE=3;
    static final int INSERT_BRANCH_CITY=4;
    static final int INSERT_BRANCH_STATE=5;
    static final int INSERT_BRANCH_PINCODE=6;
    static final int INSERT_BRANCH_STATUS=7;

    static final int DELETE_BRANCH_ID=1;

    static final int UPDATE_BRANCH_IFSC=1;
    static final int UPDATE_BRANCH_ID=2;


    private static final Logger LOG = LoggerFactory.getLogger(BranchDAO.class);

    static final String INSERT_SQL = "Insert into Branch (branchId ,branchName ,ifscCode ,city ,state ,pincode ,status) values(?,?,?,?,?,?,?) ";
    static final String SELECT_SQL ="select * from Branch";
    static final String DELETE_SQL ="Delete from Branch where branchId=?";
    static final String UPDATE_SQL ="Update Branch set ifscCode =? where branchId=?";

    public void insert(Branch branch){
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
          ps.setInt(INSERT_BRANCH_ID,branch.getBranchId());
          ps.setString(INSERT_BRANCH_NAME,branch.getBranchName());
          ps.setString(INSERT_BRANCH_IFSCCODE,branch.getIfscCode());
          ps.setString(INSERT_BRANCH_CITY,branch.getCity());
          ps.setString(INSERT_BRANCH_STATE,branch.getState());
          ps.setString(INSERT_BRANCH_PINCODE,branch.getPincode());
          ps.setString(INSERT_BRANCH_STATUS,branch.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                LOG.error("Insert failed, no rows affected for Branch: {}", branch.getBranchId());

            } else {
                LOG.info("Successfully inserted account: {}", branch.getBranchId());
            }

        } catch (SQLException e) {
            throw new DataException("Failed:",e);
        }
    }

    public List<Branch> findAll() {
        List<Branch> branchList =new ArrayList<>();
        try (Connection con = DBConnection.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_SQL)){

            ResultSet rs =ps.executeQuery();
            while (rs.next()) {
                branchList.add(mapping(rs));
            }

            LOG.info("Successfully fetched all Branch, count={}", branchList.size());

            return branchList;


        } catch (SQLException e ) {
            throw new DataException("Failed to fetch Branch ",e);
        }
    }
    public Branch mapping(ResultSet rs)throws SQLException{
        Branch branch =new Branch();
        branch.setBranchId(rs.getInt("branchId"));
        branch.setBranchName(rs.getString("branchName"));
        branch.setIfscCode(rs.getString("ifscCode"));
        branch.setCity(rs.getString("city"));
        branch.setState(rs.getString("state"));
        branch.setPincode(rs.getString("pincode"));
        branch.setStatus(rs.getString("status"));
        return branch;
    }

    public void delete(int id) {
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_SQL)){
            ps.setInt(DELETE_BRANCH_ID,id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                LOG.error("Delete failed: no Branch found with this id:{}", id);

            } else {
                LOG.info("Successfully deleted Branch with id:{}", id);

            }


        } catch (SQLException e) {
            throw new DataException("Failed to Delete Branch ",e);
        }
    }

    public void updateBranch(String ifsc ,int id)  {
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps =con.prepareStatement(UPDATE_SQL)){
            ps.setString(UPDATE_BRANCH_IFSC,ifsc);
            ps.setInt(UPDATE_BRANCH_ID,id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                LOG.error("Update failed");

            } else {
                LOG.info("Successfully updated account with id={}", id);
            }

        } catch (SQLException e) {
            throw new DataException("Failed to Update",e);
        }
    }
}
