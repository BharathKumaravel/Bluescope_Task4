package org.example;


import org.example.dbconfig.ConnectionClass;
import org.example.exception.DataException;
import org.example.model.Branch;
import org.example.repository.BranchDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static java.lang.Class.forName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BranchDAOTest {

    private BranchDAO branchDAO;

    @BeforeEach
    void setup() throws Exception {

        branchDAO = new BranchDAO();

        try (

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankBranchManagement","root","root");) {
            Statement stmt = con.createStatement();
            stmt.execute("Delete from employee");
            stmt.execute("Delete from Branch");
        }

    }

    @Test
    void testInsert() {

        Branch branch = createBranch();

        branchDAO.insert(branch);

        List<Branch> branches = branchDAO.findAll();
        assertEquals(1, branches.size());
        assertEquals("ChennaiBranch", branches.get(0).getBranchName());
    }
    @Test
    void testInsertInvalid() {

        Branch branch = new Branch();
        try {
            branchDAO.insert(branch);
        } catch (DataException e) {
            assertNotNull(e.getMessage());
            assertEquals("Failed:", e.getMessage());
        }
    }


    @Test
    void testFindAll() {

        branchDAO.insert(createBranch());

        List<Branch> branches = branchDAO.findAll();

        assertFalse(branches.isEmpty());
    }

    @Test
    void testDelete() {

        branchDAO.insert(createBranch());

        branchDAO.delete(1);

        assertEquals(0, branchDAO.findAll().size());
    }

    @Test
    void testUpdateBranch() {

        branchDAO.insert(createBranch());

        branchDAO.updateBranch("NEWIFSC123", 1);

        Branch updated = branchDAO.findAll().get(0);
        assertEquals("NEWIFSC123", updated.getIfscCode());
    }


    private Branch createBranch() {

        Branch b = new Branch(1,"ChennaiBranch","KKBK000123","Chennai","TamilNadu","600100","ACTIVE");
        return b;
    }
}