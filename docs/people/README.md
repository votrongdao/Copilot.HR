# People Management Module Documentation

Comprehensive architectural documentation for the **People Management** module in **Copilot.HR**, including Use Case diagrams, UI/UX specifications, Database Schema ERDs, API tree architecture, and interactive Swagger API documentation.

---

## 1. Use Case Diagram

The People Management Use Case Diagram defines the primary system interactions between System Actors (Staff, HR Staff, Manager, HR Manager, Tenant Admin) and key module capabilities (Directory, Profile, Org Tree, Request Workflow).

![People Management Use Case Diagram](../../images/usecase/People.png)

---

## 2. UI/UX Specifications

User interface screens, slide-over drawers, and modal dialogs designed for the People Management module following minimal monochrome wireframe aesthetics.

### 2.1 Main Screens & Sub-Screens

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

### 2.2 Major PopUp Modals & Drawers

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

## 3. Database Schema ERD

Entity Relationship Diagrams (ERD) illustrating the relational schema across Organization, Employee Directory, and Request Management domains.

#### Organization Domain Schema ERD
Database relationship model covering Company Branches, Departments, Teams, Positions, and Reporting Line relationships.

![Organization Domain ERD](../../images/databases/Organization.png)

---

#### Employee Directory Domain Schema ERD
Database entity structure covering Employee Accounts, 1:1 Profiles, Labor Contracts, Verification Documents, Education, and Assets.

![Employee Directory ERD](../../images/databases/EmployeeDirectory.png)

---

#### Request Management Domain Schema ERD
Workflow database model covering Ticket Requests, Request Types, Workflow Steps, Multi-Stage Approval Logs, and Attachments.

![Request Management ERD](../../images/databases/Request_Managment.png)

---

## 4. API Architecture Tree Structure

High-level tree view of all 35 RESTful API endpoints in the People Management module grouped into logical domain controllers.

Hierarchical API endpoint tree diagram illustrating resource routing for Employees, Contracts, Documents, Quotas, Audit History, Departments, Positions, Teams, Reporting Lines, and Request Management.

![People Management API Architecture Tree](../apis/api_structure.png)

---

## 5. Interactive Swagger API Documentation

Access the interactive online Swagger API documentation for real-time request testing, request/response schema inspection, and live endpoint execution:

👉 **[Copilot.HR Employee Directory API - Interactive SwaggerHub Documentation](https://app.swaggerhub.com/apis/ouuniversity/copilothr-employee-directory-api/1.0.0#/Documents)**
