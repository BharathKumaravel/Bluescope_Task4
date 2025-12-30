package org.example.model;

public class EmployeeUpdate {
    private int id;
    private String Designation;
   public EmployeeUpdate(){}
    public EmployeeUpdate(int id, String designation) {
        this.id = id;
        Designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}
