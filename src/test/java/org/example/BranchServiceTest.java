package org.example;

import org.example.dao.BranchDAO;
import org.example.model.Branch;
import org.example.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BranchServiceTest {

    private BranchService branchService;
    private BranchDAO branchDAO;

    @BeforeEach
    void setup() throws Exception {

        branchService = new BranchService();
        branchDAO = mock(BranchDAO.class);


        var field = BranchService.class.getDeclaredField("branchDAO");
        field.setAccessible(true);
        field.set(branchService, branchDAO);
    }

    @Test
    void testInsert() {

        Branch branch = new Branch();

        branchService.insert(branch);

        verify(branchDAO).insert(branch);
    }

    @Test
    void testFindAll() {

        List<Branch> branches = List.of(new Branch());
        when(branchDAO.findAll()).thenReturn(branches);

         Object  result = branchService.findAll();

        assertEquals(branches, result);
        verify(branchDAO).findAll();
    }

    @Test
    void testDelete() {

        branchService.delete(1);

        verify(branchDAO).delete(1);
    }

    @Test
    void testUpdateBranch() {

        branchService.updateBranch("NEWIFSC", 10);

        verify(branchDAO).updateBranch("NEWIFSC", 10);
    }
}