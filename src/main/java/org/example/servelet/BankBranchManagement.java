package org.example.servelet;

import com.fasterxml.jackson.databind.ObjectMapper;

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
public class BankBranchManagement extends HttpServlet {


    Logger LOG = LoggerFactory.getLogger(BankBranchManagement.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    BranchService branchService =new BranchService();
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){


        try {
            Branch branch  = objectMapper.readValue(request.getInputStream(), Branch.class);
             branchService.insert(branch);
             response.setStatus(200);
             LOG.info("Account created successfully: {}", branch.getBranchId());
        }
        catch (IOException ex) {
            LOG.error("Failed to create Branch, EXCEPTION: ", ex);
            response.setStatus(406);
        }
    }
    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response)
    {
        try{
        mapBranch(response);
        LOG.info("Fetched all Branches successfully");
        }
        catch (Exception e)
        {
            LOG.error("Failed to get , EXCEPTION: ", e);
        }

    }
    private void mapBranch(HttpServletResponse response) {
        try {
            objectMapper.writeValue(response.getWriter(), branchService.findAll());
        }
        catch (IOException e) {
            LOG.error("Failed to fetch branch", e);
            response.setStatus(500);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request , HttpServletResponse response){
        try{
        int id = Integer.parseInt(request.getParameter("id"));
        branchService.delete(id);
        response.setStatus(200);
        LOG.info("Branch Deleted Successfully..");}
        catch (Exception e)
        {
            LOG.error("Failed to delete, EXCEPTION: "+e);
            response.setStatus(500);
        }
    }

    @Override
    public void doPut(HttpServletRequest request , HttpServletResponse response){
        try{
            BranchUpdate branchUpdate = objectMapper.readValue(request.getInputStream(),BranchUpdate.class);
            branchService.updateBranch(branchUpdate.getIfscCode(),branchUpdate.getBranchId());
            response.setStatus(200);
            LOG.info("Updated Successfully");
        }
        catch (Exception e)
        {
            LOG.error("Failed to Update , EXCEPTION: ",e);
        }
    }


}


