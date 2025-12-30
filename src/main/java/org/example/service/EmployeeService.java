package org.example.service;

import org.example.model.Branch;
import org.example.model.Employee;
import org.example.repository.EmployeeDAO;

public class EmployeeService {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    public void insert(Employee employee) {
        employeeDAO.insert(employee);
    }
    public void delete(int id) {employeeDAO.delete(id);}
    public void updateEmployee(String ifscCode, int branchId) {
        employeeDAO.updateEmployee(ifscCode,branchId);
    }
}

