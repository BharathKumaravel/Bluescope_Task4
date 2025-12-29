package org.example.service;

import org.example.model.Branch;
import org.example.repository.BranchRepository;

public class BranchService {

    BranchRepository branchRepository =new BranchRepository();
    public void insert(Branch branch) {
        branchRepository.insert(branch);
    }

    public Object findAll() {
        return branchRepository.findAll();
    }

    public void delete(int id) {
        branchRepository.delete(id);
    }

    public void updateBranch(String ifscCode, int branchId) {
        branchRepository.updateBranch(ifscCode,branchId);
    }
}
