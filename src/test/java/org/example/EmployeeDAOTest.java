package org.example;

import org.example.exception.DataException;
import org.example.model.Employee;
import org.example.dao.EmployeeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;

    @BeforeEach
    void set() throws SQLException {
        employeeDAO = new EmployeeDAO();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankBranchManagement","root","root");
        Statement stmt =con.createStatement();
        stmt.execute("Delete from employee");
    }
    @Test
    void testInsert(){

        employeeDAO.insert(createEmployee());

        List<Employee> employeeList=employeeDAO.getEmployeeByBranchId(1);
        assertEquals(1,employeeList.size());
        assertEquals("Bharath",employeeList.get(0).getEmployeeName());
    }
    @Test
    void testInsertInvalid(){
        try {
            employeeDAO.insert(createEmployeeInvalid());

        }
        catch (DataException e)
        {
        assertNotNull(e);
        assertEquals("Failde to Insert Employee",e.getMessage());
        }

    }

    @Test
    void testDelete(){

        employeeDAO.insert(createEmployee());

        employeeDAO.delete(1);
        assertEquals(0,employeeDAO.getEmployeeByBranchId(1).size());

    }



    @Test
    void testDeleteInvalid(){

      try{

        employeeDAO.delete(-1);}
      catch (DataException e) {
          assertNotNull(e);
          assertEquals("Failed to delete Employee",e.getMessage());
      }

    }

    @Test
    void testUpdate(){
       employeeDAO.insert(createEmployee());

       employeeDAO.updateEmployee("Clerk", 1);

        Employee updated = employeeDAO.getEmployeeByBranchId(1).get(0);
        assertEquals("Clerk", updated.getDesignation());
    }





    @Test
    void testGet(){
        employeeDAO.insert(createEmployee());

        List<Employee> employeeList=employeeDAO.getEmployeeByBranchId(1);
        assertFalse(employeeList.isEmpty());
    }

    public Employee createEmployee(){
        Employee employee = new Employee(1,1,"Bharath","Manager");
        return employee;
    }
    public Employee createEmployeeInvalid(){
        Employee employee = new Employee();
        return employee;
    }
}
