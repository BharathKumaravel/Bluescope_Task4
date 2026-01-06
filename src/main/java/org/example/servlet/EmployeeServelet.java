package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Employee;
import org.example.model.EmployeeUpdate;
import org.example.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/employee")
public class EmployeeServelet extends HttpServlet {
    private static final long serialVersionUID=1L;
    static Logger LOG = LoggerFactory.getLogger(EmployeeServelet.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    final EmployeeService employeeService =new EmployeeService();

  @Override
    public void doPost(HttpServletRequest request , HttpServletResponse response)
    {

        try {
             Employee employee  = objectMapper.readValue(request.getInputStream(), Employee.class);
            employeeService.insert(employee);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            LOG.info("Employee created successfully: {}", employee.getEmployeeId());
        }
        catch (IOException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to insert Employee",e);
        }
    }
    @Override
    public void doDelete(HttpServletRequest request , HttpServletResponse response){
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            employeeService.delete(id);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            LOG.info("Employee Deleted Successfully..");}
        catch (Exception e)
        {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to Delete Employee",e);
        }
    }

    @Override
    public void doPut(HttpServletRequest request , HttpServletResponse response){
        try{
            EmployeeUpdate employeeUpdate = objectMapper.readValue(request.getInputStream(), EmployeeUpdate.class);
            employeeService.updateEmployee(employeeUpdate.getDesignation(),employeeUpdate.getId());
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            LOG.info("Updated Successfully");
        }
        catch (Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            LOG.error("Failed to Update Employee",e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)
    {

        try{
            int branchId = Integer.parseInt(request.getParameter("branchId"));
        List<Employee> employees = employeeService.getByBranchId(branchId);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(employees);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(json);}
        catch (Exception e)
        {
            LOG.error("Failed :",e);
        }
    }


}

