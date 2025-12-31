# Bank Branch Management - Java Servelet and JDBC

## Description
BankBranchManagement is a Java Servlet-based project that manages bank branches and employees using MySQL.
The application is packaged as a WAR file and deployed on an Apache Tomcat server.
It demonstrates basic servlet handling, database connection, and data management.

---

## Features
- Create, modify, remove, and retrieve Branch and Employee records

- Implemented using plain Java Servlets without any frameworks

- Data stored and accessed through MySQL with direct JDBC integration

- Shared database connection utility with structured error handling

- Repository-level tests executed against an actual MySQL instance

- Servlet behavior verified using Mockito-based test cases

- Packaged as a WAR using Maven and deployed on Tomcat

## Database Schema
   This Application use a MYSQL for Database

   ### Branch Table
   Stores Branch Information

   ```sql
create table Branch(branchId int not null primary key,
                    branchName Varchar(30),
                    ifscCode varchar(15) not null,
                    city varchar(30),
                    state varchar(30),
                    pincode varchar(6) not null,
                    status varchar(10) not null);
```
### Columns:

 - branch_id: Primary key , not null
 - branchName: Branch Name (30 characters max)
 - ifscCode:  Branch IFSC code , not null
 - city: Branch City
 - state: Branch State
 - pincode: pincode of the branch
 - status: Bransh status (ACTIVE, INACTIVE)

   ---


   ### Employee Table
   Store Employee Information
   ```sql
   create table employee(branchId int not null,
                      employeeId int not null primary key,
                      employeeName varchar(30),
                      Designation varchar(30) not null,
                      foreign key(branchId) references Branch(branchId));
   ```
   ### Columns:

   -branch_id: not null
   -employeeId: Primary Key not null,
   -employeeName: Name of the employee
   -designation: Employee Designation ,not null

   ---

   ## Example API Requests and Responses

### Branch Endpoints (/branch)

| Operation   | HTTP Method | URL                            |
|-------------|-------------|--------------------------------|
| Create      | POST        | /branch                      |
| Get All     | GET         | /loan/account                |
| Update      | PUT         | /branch              |
| Delete      | DELETE      | /branch?id={branchId} |


  ---

  
### Request and Response
*Request Body(JSON)*

## Insert Branch
*Request*
*Post/branch*
json
{
  "branchId":4,
	"branchName": "chennaiBranch",
	"ifscCode": "KKBK000123",
	"city": "chennai",
	"state": "Tamil Nadu",
	"pincode": "600100",
	"status": "ACTIVE"
  }

*Response(JSON)*
 - Status: 202 Created

  ## Get Branch
 *Request*
    *Get/branch*

 *Response*
 json
{
  "branchId":4,
	"branchName": "chennaiBranch",
	"ifscCode": "KKBK000123",
	"city": "chennai",
	"state": "Tamil Nadu",
	"pincode": "600100",
	"status": "ACTIVE"
  }


     

 





