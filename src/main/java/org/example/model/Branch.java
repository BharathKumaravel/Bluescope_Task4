package org.example.model;

public class Branch {
   private int branchId;
   private String branchName;
   private String ifscCode;
   private String city;
   private String state;
   private String pincode;
   private String status;

    public Branch(int branchId, String branchName, String ifscCode, String city, String state, String pincode, String status) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.ifscCode = ifscCode;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.status = status;
    }
    public Branch(){

    }

    public Branch(int branchId) {
        this.branchId = branchId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
