package org.example.model;

public class Employee {
    private int employeeId;
    private int branchId;
    private String  employeeName;
    private String designation;
  public Employee(){}
    public Employee(int employeeId, int branchId, String employeeName, String designation) {
        this.employeeId = employeeId;
        this.branchId = branchId;
        this.employeeName = employeeName;
        this.designation = designation;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
       this.designation = designation;
    }
}
