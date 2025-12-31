package org.example;


import org.example.exception.DataException;
import org.example.model.Branch;
import org.example.repository.BranchDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BranchDAOTest {

    private BranchDAO branchDAO;

    @BeforeEach
    void setup() throws Exception {

        branchDAO = new BranchDAO();


        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/BankBranchManagement",
                "root",
                "root"
        )) {
            Statement stmt = con.createStatement();
            stmt.execute("DELETE FROM Branch");
        }
    }

    @Test
    void testInsert() {

        Branch branch = createBranch();

        branchDAO.insert(branch);

        List<Branch> brancheList = branchDAO.findAll();
        assertEquals(1, brancheList.size());
        assertEquals("Chennai", brancheList.get(0).getBranchName());
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

        Branch b = new Branch();
        b.setBranchId(1);
        b.setBranchName("Chennai");
        b.setIfscCode("IFSC001");
        b.setCity("Chennai");
        b.setState("TN");
        b.setPincode("600001");
        b.setStatus("ACTIVE");
        return b;
    }
}