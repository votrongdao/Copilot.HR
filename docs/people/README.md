# People Management Module Documentation

Comprehensive architectural documentation for the **People Management** module in **Copilot.HR**, including Use Case diagrams, Information Architecture (IA) sitemap, UI/UX specifications, Database Schema ERDs, API tree architecture, and interactive Swagger API documentation.

---

## 1. Use Case Diagram

The People Management Use Case Diagram defines the primary system interactions between System Actors (Staff, HR Staff, Manager, HR Manager, Tenant Admin) and key module capabilities (Directory, Profile, Org Tree, Request Workflow).

![People Management Use Case Diagram](../../images/usecase/People.png)

---

## 2. Information Architecture (IA) Sitemap

![People Management Information Architecture Sitemap](../../images/sitemap/people-sitemap.png)

---

## 3. UI/UX Specifications

User interface screens, slide-over drawers, and modal dialogs designed for the People Management module following minimal monochrome wireframe aesthetics.

### 3.1 Main Screens & Sub-Screens

#### Employee Directory Screen
Central workforce catalog featuring searchable employee records, KPI metrics, status filters, and quick action toolbars.

![Employee Directory Screen](../../images/uiux/people/EmployeeDirectory.png)

---

#### Employee Profile Detail Screen
Dedicated 360-degree employee profile view with breadcrumb navigation, header banner, and consolidated tabs for Overview, Contract & Documents, Education, and Audit History.

![Employee Profile Detail Screen](../../images/uiux/people/EmployeeProfileDetail.png)

---

#### Organization & Department Screen
Interactive organizational structure canvas featuring department hierarchy tree, roster headcount metrics, branch location filters, and zoom controls.

![Organization & Department Screen](../../images/uiux/people/OrgDepartment.png)

---

#### Request Management Screen
Management dashboard for employee HR requests, leave approvals, status filtering, and workflow processing.

![Request Management Screen](../../images/uiux/people/RequestManagement.png)

---

#### Create HR Request Screen
Two-column interactive form for submitting annual leave, equipment, or policy requests with automatic quota balance validation and document uploads.

![Create HR Request Screen](../../images/uiux/people/CreateRequest.png)

---

#### Tracking Request Progress Screen
Real-time request progress tracker displaying step-by-step approval workflow stages, reviewer audit logs, and timeline timestamps.

![Tracking Request Progress Screen](../../images/uiux/people/TrackingRequest.png)

---

#### Position & Job Title Management Screen
Management screen defining organizational job titles, competency levels (L1-L6), salary band ranges, and headcount quotas.

![Position Management Screen](../../images/uiux/people/PositionManagement.png)

---

#### Team Management Screen
Workspace for organizing cross-functional project teams, designating team leads, and allocating member capacity.

![Team Management Screen](../../images/uiux/people/TeamManagement.png)

---

#### Reporting Lines & Hierarchy Matrix Screen
Organizational matrix displaying direct report supervisors, functional line managers, and reporting relationships.

![Reporting Lines Matrix Screen](../../images/uiux/people/ReportingLines.png)

---

### 3.2 Major PopUp Modals & Drawers

#### Add New Employee Profile Modal
Modal popup form for registering a new employee profile with personal demographics, corporate email, role assignment, and department placement.

![Add Employee Modal](../../images/uiux/people/AddEmployeeModal.png)

---

#### Add Department Drawer
Slide-over drawer for configuring new department entities, parent division alignment, location branch, and department lead assignment.

![Add Department Drawer](../../images/uiux/people/AddDepartmentDrawer.png)

---

#### Add Labor Contract Modal
Form popup for registering official labor contracts, compensation terms, effective start/end dates, and document attachments.

![Add Labor Contract Modal](../../images/uiux/people/AddContractModal.png)

---

## 4. Database Schema ERD

Entity Relationship Diagrams (ERD) illustrating the relational schema across Organization, Employee Directory, and Request Management domains.

#### Organization Domain Schema ERD
Database relationship model covering Company Branches, Departments, Teams, Positions, and Reporting Line relationships.

![Organization Domain ERD](../../images/databases/people/Organization.png)

---

#### Employee Directory Domain Schema ERD
Database entity structure covering Employee Accounts, 1:1 Profiles, Labor Contracts, Verification Documents, Education, Assets, Leave Types, and Leave Balances.

![Employee Directory ERD](../../images/databases/people/EmployeeDirectory.png)

---

#### Request Management Domain Schema ERD
Workflow database model covering Ticket Requests, Request Types, Workflow Steps, Multi-Stage Approval Logs, and Attachments.

![Request Management ERD](../../images/databases/people/Request_Managment.png)

---

## 6. Interactive Swagger API Documentation

Access the interactive online Swagger API documentation for real-time request testing, request/response schema inspection, and live endpoint execution:

[Copilot.HR Employee Directory API - Interactive SwaggerHub Documentation](https://app.swaggerhub.com/apis/ouuniversity/copilothr-employee-directory-api/1.0.0#/Documents)

### 6.1 API Endpoint Tree Structure

```text
Copilot.HR - People Management API
│
├── Employees & Profiles
│   ├── GET     /employees
│   ├── POST    /employees
│   ├── POST    /employees/export
│   ├── GET     /employees/{id}
│   ├── PUT     /employees/{id}
│   ├── DELETE  /employees/{id}
│   ├── GET     /employees/{id}/contracts
│   ├── POST    /employees/{id}/contracts
│   ├── GET     /employees/{id}/documents
│   ├── POST    /employees/{id}/documents
│   └── GET     /employees/{id}/history
│
├── Leave Categories & Balances
│   ├── GET     /leave-types
│   ├── POST    /leave-types
│   ├── GET     /employees/{id}/leave-balance
│   └── GET     /requests/quotas/check
│
├── HR Request Management & Workflows
│   ├── GET     /requests
│   ├── POST    /requests
│   ├── GET     /requests/{id}
│   ├── PUT     /requests/{id}
│   ├── POST    /requests/{id}/approve
│   ├── POST    /requests/{id}/reject
│   ├── POST    /requests/{id}/cancel
│   └── GET     /requests/{id}/timeline
│
└── Organization & Hierarchy Matrix
    ├── GET     /departments
    ├── POST    /departments
    ├── GET     /departments/{id}
    ├── PUT     /departments/{id}
    ├── DELETE  /departments/{id}
    ├── POST    /departments/restructure
    ├── GET     /positions
    ├── POST    /positions
    ├── PUT     /positions/{id}
    ├── GET     /teams
    ├── POST    /teams
    ├── POST    /teams/{id}/members
    ├── GET     /reporting-lines
    └── PUT     /reporting-lines
```

### 6.2 Swagger UI Endpoint Documentation Screenshots

#### Employee Directory APIs
Swagger interactive endpoint documentation for searching, creating, and fetching employee profiles.

![Employee Directory APIs](./EmployeeApi.png)

---

#### Labor Contracts & Documents APIs
Swagger documentation for managing labor contracts, salary history, and employee document uploads.

![Labor Contracts and Documents APIs](./Contract_Documents.png)

---

#### Leave & Audit History APIs
Swagger documentation for fetching career audit trails and event logs.

![Leave and Audit History APIs](./Leave_History.png)

---

#### Leave Categories & Quotas APIs
Swagger documentation for leave category configurations and leave balance quota validation.

![Report Quotas APIs](./Report_Quotas.png)

---

#### Request Management APIs
Swagger documentation for submitting, filtering, and managing employee HR requests.

![Request Management APIs](./Request.png)

---

#### Approvals & Workflow Tracking APIs
Swagger documentation for multi-level approval routing, decision processing, and workflow timeline tracking.

![Approvals and Tracking APIs](./Approvals_Tracking.png)

---

#### Department Management APIs
Swagger documentation for department tree hierarchy, creation, modification, and organizational restructuring.

![Department Management APIs](./Department.png)

---

#### Positions, Teams & Reporting Lines APIs
Swagger documentation for position titles, job levels, project teams, and reporting line hierarchy matrix.

![Positions Teams and Reporting Lines APIs](./Position_Team.png)
