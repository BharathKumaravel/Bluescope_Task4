package org.example.model;

public class BranchUpdate {
    private int branchId;
    private String ifscCode;
    public BranchUpdate(){}
    public BranchUpdate(int branchId, String ifscCode) {
        this.branchId = branchId;
        this.ifscCode = ifscCode;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
