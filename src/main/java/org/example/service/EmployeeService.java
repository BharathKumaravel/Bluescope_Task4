package org.example.service;
import org.example.model.Employee;
import org.example.dao.EmployeeDAO;

import java.util.List;

public class EmployeeService {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    public void insert(Employee employee) {
        employeeDAO.insert(employee);
    }
    public void delete(int id) {employeeDAO.delete(id);}
    public void updateEmployee(String designation, int employeeId) {
        employeeDAO.updateEmployee(designation,employeeId);
    }
    public List<Employee> getByBranchId(int branchId){return employeeDAO.getEmployeeByBranchId(branchId);}
}

