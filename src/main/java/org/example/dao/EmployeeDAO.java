package org.example.dao;

import org.example.config.DBConnection;
import org.example.exception.DataException;
import org.example.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    static final int INSERT_BRANCH_ID =1;
    static final int INSERT_EMPLOYEE_ID=2;
    static final int INSERT_EMPLOYEE_NAME=3;
    static final int INSERT_EMPLOYEE_DESIGNATION=4;

    static final int DELETE_EMPLOYEE_ID=1;

    static final int UPDATE_EMPLOYEE_DESIGNATION=1;
    static final int UPDATE_EMPLOYEE_ID=2;

    static  final int GET_EMPLOYEE_BY_BRANCHID=1;

   private static final Logger LOG = LoggerFactory.getLogger(EmployeeDAO.class);

    static final String INSERT_SQL = "Insert into employee (branchId,employeeId,employeeName,designation) values(?,?,?,?)";
    static final String DELETE_SQL ="Delete from employee where employeeId=?";
    static final String UPDATE_SQL ="Update employee set designation=? where employeeId=?";
    static final String GET_BY_BRANCHID_SQL ="select * from Employee where branchId=?";

    public void insert(Employee employee){
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(INSERT_BRANCH_ID,employee.getBranchId());
            ps.setInt(INSERT_EMPLOYEE_ID,employee.getEmployeeId());
            ps.setString(INSERT_EMPLOYEE_NAME,employee.getEmployeeName());
            ps.setString(INSERT_EMPLOYEE_DESIGNATION,employee.getDesignation());


            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                LOG.error("Insert failed, no rows affected for Employee: {}", employee.getEmployeeId());

            } else {
                LOG.info("Successfully inserted Employee: {}", employee.getEmployeeId());
            }

        } catch (SQLException e) {
            throw new DataException("Failde to Insert Employee",e);
        }


    }
    public void delete(int id) {
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_SQL)){
            ps.setInt(DELETE_EMPLOYEE_ID,id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                LOG.error("Delete failed: no Employee found with this id:{}", id);

            } else {
                LOG.info("Successfully deleted Employee with id:{}", id);

            }


        } catch (Exception e) {
            throw new DataException("Failed to delete Employee",e);
        }
    }
    public void updateEmployee(String designation ,int id)  {
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps =con.prepareStatement(UPDATE_SQL)){
            ps.setString(UPDATE_EMPLOYEE_DESIGNATION,designation);
            ps.setInt(UPDATE_EMPLOYEE_ID,id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                LOG.error("Update failed");

            } else {
                LOG.info("Successfully updated employee with id={}", id);
            }

        } catch (SQLException e) {
            throw new DataException("Failed to update employee",e);
        }
    }

    public List<Employee> getEmployeeByBranchId(int branchId) {
        try(Connection con = DBConnection.getConnect().getConnection();
            PreparedStatement ps = con.prepareStatement(GET_BY_BRANCHID_SQL)){
            ps.setInt(GET_EMPLOYEE_BY_BRANCHID,branchId);
            ResultSet rs = ps.executeQuery();
            List<Employee> employeeList = new ArrayList<>();
            while(rs.next()){
                employeeList.add(mapping(rs));
            }
            return employeeList;

        } catch (SQLException e) {
            throw new DataException("Failed to get Employee",e);
        }
    }
    public Employee mapping(ResultSet rs) throws SQLException {
        Employee employee =new Employee();
        employee.setEmployeeName(rs.getString("employeeName"));
        employee.setEmployeeId(rs.getInt("employeeId"));
        employee.setDesignation(rs.getString("Designation"));
        employee.setBranchId(rs.getInt("branchId"));
        return employee;
    }

}
