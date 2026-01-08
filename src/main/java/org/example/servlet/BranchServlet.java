package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.model.Branch;
import org.example.model.BranchUpdate;


import org.example.service.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.IOException;
import java.util.List;


public class
BranchServlet extends HttpServlet {
    private static final long serialVersionUID=1L;
    private static final Logger LOG = LoggerFactory.getLogger(BranchServlet.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

   private  BranchService branchService =new BranchService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            Branch branch = objectMapper.readValue(request.getInputStream(), Branch.class);
            branchService.insert(branch);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            LOG.info("Branch created successfully: {}", branch.getBranchId());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to insert Branch, EXCEPTION: ", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response) {
        try{
            List<Branch> branches = (List<Branch>) branchService.findAll();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(branches);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json);
            LOG.info("Fetched all Branches successfully");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to get Branch , EXCEPTION: ", e);
        }

    }

    @Override
    public void doDelete(HttpServletRequest request , HttpServletResponse response){
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            branchService.delete(id);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            LOG.info("Branch Deleted Successfully..");
        } catch (Exception e) {
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
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Failed to Update Branch , EXCEPTION: ",e);
        }
    }


}


