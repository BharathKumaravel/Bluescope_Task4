package org.example.service;

import org.example.model.Branch;
import org.example.repository.BranchDAO;

public class BranchService {

    BranchDAO branchDAO =new BranchDAO();
    public void insert(Branch branch) {
        branchDAO.insert(branch);
    }

    public Object findAll() {
        return branchDAO.findAll();
    }

    public void delete(int id) {
        branchDAO.delete(id);
    }

    public void updateBranch(String designation, int employeeId) {
        branchDAO.updateBranch(designation,employeeId);
    }
}
