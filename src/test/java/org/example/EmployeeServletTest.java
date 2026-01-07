package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.DataException;
import org.example.model.Employee;
import org.example.model.EmployeeUpdate;
import org.example.servlet.EmployeeServlet;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

class EmployeeServletTest {
    ObjectMapper objectMapper;
    private EmployeeServlet employeeServlet;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private EmployeeService employeeService;

    @BeforeEach
    void set() throws  Exception{
        objectMapper=new ObjectMapper();
        employeeServlet =new EmployeeServlet();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        employeeService =mock(EmployeeService.class);

        var field = EmployeeServlet.class.getDeclaredField("employeeService");
        field.setAccessible(true);
        field.set(employeeServlet, employeeService);
    }

    @Test
    void testDoPost() throws Exception
    {
        Employee employee =new Employee();
        employee.setEmployeeId(1);
        employee.setBranchId(1);
        employee.setEmployeeName("Bharath");
        employee.setDesignation("Manager");

        String json = objectMapper.writeValueAsString(employee);
        when(request.getInputStream()).thenReturn(inputStream(json));

        employeeServlet.doPost(request,response);

        verify(employeeService).insert(any(Employee.class));
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDoPostInvalid() throws Exception{

        when(request.getInputStream()).thenThrow(IOException.class);
        try{


            employeeServlet.doPost(request,response);
        }
        catch (DataException  e)
        {
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void testDoDelete(){
        when(request.getParameter("id")).thenReturn("1");
        employeeServlet.doDelete(request, response);

        verify(employeeService).delete(1);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }
 @Test
 void testDoDeleteInvalid(){
     when(request.getParameter("id")).thenReturn(null);
     try
     {
         employeeServlet.doDelete(request, response);
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

        employeeServlet.doPut(request, response);

        verify(employeeService).updateEmployee("Manager", 1);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }
    @Test
    void testDoPutInvalid() throws IOException{
        when(request.getInputStream()).thenThrow(IOException.class);
        try {

            employeeServlet.doPut(request, response);
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
