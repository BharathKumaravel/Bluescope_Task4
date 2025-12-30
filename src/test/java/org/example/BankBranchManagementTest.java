package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.DataException;
import org.example.model.Branch;
import org.example.model.BranchUpdate;
import org.example.servelet.BankBranchManagement;
import org.example.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.ReadListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankBranchManagementTest {

    ObjectMapper objectMapper =new ObjectMapper();
    private BankBranchManagement branchServelet;
    private HttpServletResponse response;
    private  HttpServletRequest request;
    private BranchService branchService;


    @BeforeEach
    void set() throws Exception{
        branchServelet =new BankBranchManagement();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        branchService= mock(BranchService.class);

        var field = BankBranchManagement.class.getDeclaredField("branchService");
        field.setAccessible(true);
        field.set(branchServelet, branchService);

    }
    @Test
    void TestdoPost() throws Exception {
        Branch branch =new Branch();
        branch.setBranchId(1);
        branch.setBranchName("ChennaiBranch");
        branch.setCity("chennai");
        branch.setPincode("600100");
        branch.setState("Tamil Nadu");
        branch.setStatus("ACTIVE");
        branch.setIfscCode("KKBK000123");

        String json = objectMapper.writeValueAsString(branch);
        when(request.getInputStream()).thenReturn(inputStream(json));

        branchServelet.doPost(request,response);

        verify(branchService).insert(any(Branch.class));
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);


    }
    @Test
    void testDoPostInvalid() throws Exception {
        when(request.getInputStream()).thenThrow(IOException.class);

     try{
         branchServelet.doPost(request,response);
     }
     catch (DataException e){

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);}
    }

    @Test
    void testDoGet() throws Exception {

        when(branchService.findAll()).thenReturn(List.of(new Branch()));


        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

       branchServelet.doGet(request, response);

        verify(branchService).findAll();
    }

    @Test
    void testDoDelete() {


        when(request.getParameter("id")).thenReturn("1");

        branchServelet.doDelete(request, response);

        verify(branchService).delete(1);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDoDeleteInvalid(){
        when(request.getParameter("id")).thenReturn(null);
        try{
            branchServelet.doDelete(request, response);
        }
        catch (Exception e)
        {
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Test
    void testDoPut() throws Exception {

        BranchUpdate update = new BranchUpdate();
        update.setBranchId(1);
        update.setIfscCode("NEWIFSC");

        String json = objectMapper.writeValueAsString(update);


        when(request.getInputStream()).thenReturn(inputStream(json));

       branchServelet.doPut(request, response);

        verify(branchService).updateBranch("NEWIFSC", 1);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDoPutInvalid() throws IOException{
        when(request.getInputStream()).thenThrow(IOException.class);
        try {

            branchServelet.doPut(request, response);
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