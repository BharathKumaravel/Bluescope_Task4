package org.example.repository;

import org.example.dbconfig.ConnectionClass;
import org.example.model.Branch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchRepository {
    Logger LOG = LoggerFactory.getLogger(BranchRepository.class);
    String InsertSQl = "Insert into Branch (branchId ,branchName ,ifscCode ,city ,state ,pincode ,status) values(?,?,?,?,?,?,?) ";
    String SelectAllSQL ="select * from Branch";
    String DeleteSQL="Delete from Branch where branchId=?";

    String UpdateSQL="Update Branch set ifscCode =? where branchId=?";
    public void insert(Branch branch){

        try(Connection con = ConnectionClass.getConnection();
            PreparedStatement ps = con.prepareStatement(InsertSQl))
        {

          ps.setInt(1,branch.getBranchId());
          ps.setString(2,branch.getBranchName());
          ps.setString(3,branch.getIfscCode());
          ps.setString(4,branch.getCity());
          ps.setString(5,branch.getState());
          ps.setString(6,branch.getPincode());
          ps.setString(7,branch.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                LOG.error("Insert failed, no rows affected for Branch: {}", branch.getBranchId());

            } else {
                LOG.info("Successfully inserted account: {}", branch.getBranchId());
            }

        }
        catch (SQLException e)
        {

            LOG.error("Failed to Insert Branch");
        }


    }

    public List<Branch> findAll()
    {
        List<Branch> branchList =new ArrayList<>();
        try (Connection con = ConnectionClass.getConnection();
             PreparedStatement ps = con.prepareStatement(SelectAllSQL)){

            ResultSet rs =ps.executeQuery();
            while (rs.next()) {
                branchList.add(mapping(rs));
            }

            LOG.info("Successfully fetched all Branch, count={}", branchList.size());
            return branchList;
        }


        catch (SQLException e ) {
            LOG.error("Failed to fetch Branch");
            return branchList;
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

    public void delete(int id)
    {
        try(Connection con = ConnectionClass.getConnection();
            PreparedStatement ps = con.prepareStatement(DeleteSQL)){
            ps.setInt(1,id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                LOG.error("Delete failed: no Branch found with this id:{}", id);

            } else {
                LOG.info("Successfully deleted Branch with id:{}", id);

            }


        }
        catch (Exception e)
        {
            LOG.error("Failed to delete Branch");

        }
    }

    public void updateBranch(String ifsc ,int id)  {
        try(Connection con = ConnectionClass.getConnection();
            PreparedStatement ps =con.prepareStatement(UpdateSQL)){
            ps.setString(1,ifsc);
            ps.setInt(2,id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                LOG.error("Update failed");

            } else {
                LOG.info("Successfully updated account with id={}", id);
            }
        }
        catch (SQLException e)
        {
            LOG.error("Failed to update with id:"+id);

        }
    }
}
