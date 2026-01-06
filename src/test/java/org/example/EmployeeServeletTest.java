package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.DataException;
import org.example.model.Employee;
import org.example.model.EmployeeUpdate;
import org.example.servlet.EmployeeServelet;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class EmployeeServeletTest {
    ObjectMapper objectMapper;
    private EmployeeServelet employeeServelet;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private EmployeeService employeeService;

    @BeforeEach
    void set() throws  Exception{
        objectMapper=new ObjectMapper();
        employeeServelet=new EmployeeServelet();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        employeeService =mock(EmployeeService.class);

        var field = EmployeeServelet.class.getDeclaredField("employeeService");
        field.setAccessible(true);
        field.set(employeeServelet, employeeService);
    }

    @Test
    void testdoPost() throws Exception
    {
        Employee employee =new Employee();
        employee.setEmployeeId(1);
        employee.setBranchId(1);
        employee.setEmployeeName("Bharath");
        employee.setDesignation("Manager");

        String json = objectMapper.writeValueAsString(employee);
        when(request.getInputStream()).thenReturn(inputStream(json));

        employeeServelet.doPost(request,response);

        verify(employeeService).insert(any(Employee.class));
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testdoPostInvalid() throws Exception{

        when(request.getInputStream()).thenThrow(IOException.class);
        try{


            employeeServelet.doPost(request,response);
        }
        catch (DataException  e)
        {
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void testdoDelete(){
        when(request.getParameter("id")).thenReturn("1");
        employeeServelet.doDelete(request, response);

        verify(employeeService).delete(1);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }
 @Test
 void testdoDeleteInvalid(){
     when(request.getParameter("id")).thenReturn(null);
     try
     {
         employeeServelet.doDelete(request, response);
     }
     catch (DataException e)
     {

         verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
     }
 }
    @Test
    void testDoPut() throws Exception {

        EmployeeUpdate employeeUpdate =new EmployeeUpdate();
        employeeUpdate.setDesignation("Manager");
        employeeUpdate.setId(1);

        String json = objectMapper.writeValueAsString(employeeUpdate);


        when(request.getInputStream()).thenReturn(inputStream(json));

        employeeServelet.doPut(request, response);

        verify(employeeService).updateEmployee("Manager", 1);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }
    @Test
    void testDoPutInvalid() throws IOException{
        when(request.getInputStream()).thenThrow(IOException.class);
        try {

            employeeServelet.doPut(request, response);
        }
        catch (DataException e)
        {
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
    private ServletInputStream inputStream(String json) {
        ByteArrayInputStream bis =new ByteArrayInputStream(json.getBytes());
        return  new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bis.available()==0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }
}
