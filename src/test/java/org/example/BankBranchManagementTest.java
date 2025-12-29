package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Branch;
import org.example.servelet.BankBranchManagement;
import org.example.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static jdk.jfr.internal.jfc.model.Constraint.any;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankBranchManagementTest {

    @Mock
    BranchService branchService;

    @InjectMocks
    BankBranchManagement bankBranchManagement;

    @Mock
    Branch branch;

    ObjectMapper objectMapper =new ObjectMapper();
    private BankBranchManagement branchServelet;
    private HttpServletResponse response;
    private  HttpServletRequest request;


    @BeforeEach
    void set() throws Exception{
        branchServelet =new BankBranchManagement();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);

    }
    @Test
    void TestdoPost() throws Exception {
        branch.setBranchId(1);


      String json = objectMapper.writeValueAsString(branch);



        branchServelet.doPost(request,response);

        assertEquals(200,response.getStatus());

        verify(branchService.insert(any(Branch.class)));


    }


}
