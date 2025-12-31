package org.example.servelet;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.exception.DataException;
import org.example.model.Branch;
import org.example.model.BranchUpdate;

import org.example.service.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/branch")
public class BranchServelet extends HttpServlet {
    private static final long serialVersionUID=1L;


   static  Logger LOG = LoggerFactory.getLogger(BranchServelet.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

   final BranchService branchService =new BranchService();
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){


        try {
            Branch branch  = objectMapper.readValue(request.getInputStream(), Branch.class);
             branchService.insert(branch);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
             LOG.info("Branch created successfully: {}", branch.getBranchId());
        }
        catch (IOException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to insert Branch, EXCEPTION: ",e);
        }
    }
    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response)
    {
        try{
            mapBranch(response);
            LOG.info("Fetched all Branches successfully");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        catch (Exception e)
        {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to get Branch , EXCEPTION: ", e);
        }

    }
    private void mapBranch(HttpServletResponse response) {
        try {
            objectMapper.writeValue(response.getWriter(), branchService.findAll());

        }
        catch (IOException e) {

            throw new DataException("Failed to fetch branch", e);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request , HttpServletResponse response){
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            branchService.delete(id);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            LOG.info("Branch Deleted Successfully..");
        }
        catch (Exception e)
        {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to delete Branch, EXCEPTION: ",e);
        }
    }

    @Override
    public void doPut(HttpServletRequest request , HttpServletResponse response){
        try{
            BranchUpdate branchUpdate = objectMapper.readValue(request.getInputStream(),BranchUpdate.class);
            branchService.updateBranch(branchUpdate.getIfscCode(),branchUpdate.getBranchId());
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            LOG.info("Updated Successfully");

        }
        catch (Exception e)
        {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          LOG.error("Failed to Update Branch , EXCEPTION: ",e);

        }
    }


}


