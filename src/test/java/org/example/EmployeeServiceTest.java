package org.example;

import org.example.model.Employee;
import org.example.DAO.EmployeeDAO;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeDAO employeeDAO;

    @BeforeEach
    void set() throws Exception {
        employeeService =new EmployeeService();
        employeeDAO = mock(EmployeeDAO.class);
        var field = EmployeeService.class.getDeclaredField("employeeDAO");
        field.setAccessible(true);
        field.set(employeeService,employeeDAO);
    }

    @Test
    void doPost(){
        Employee employee= new Employee();
        employeeService.insert(employee);
        verify(employeeDAO).insert(employee);
    }

    @Test
    void doDelete(){
        employeeService.delete(1);
        verify(employeeDAO).delete(1);
    }


    @Test
    void doPut(){
        employeeService.updateEmployee("manager",1);
        verify(employeeDAO).updateEmployee("manager",1);
    }

    @Test
    void doGet(){

                List<Employee> employeeList =List.of(new Employee());
                when(employeeDAO.getEmployeeByBranchId(1)).thenReturn(employeeList);

                Object empl = employeeService.getByBranchId(1);
                assertEquals(empl,employeeList);
                verify(employeeDAO).getEmployeeByBranchId(1);


    }
    }


