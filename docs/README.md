# People Management - UI/UX Specifications & Screenshot Documentation

Comprehensive documentation of user interface screens, major popup modals, and slide-over drawers for the **People Management** module in **Copilot.HR**.

---

## Summary UI/UX Asset Matrix

| Category | Description | Count | Assets List |
| :--- | :--- | :---: | :--- |
| **Main Screens** | Primary application workflow and dashboard screens | **9** | `EmployeeDirectory`, `EmployeeProfileDetail`, `OrgDepartment`, `RequestManagement`, `CreateRequest`, `TrackingRequest`, `PositionManagement`, `TeamManagement`, `ReportingLines` |
| **Major Popups & Drawers** | Modal dialogs and slide-over forms for data creation and approval | **6** | `AddEmployeeModal`, `AddDepartmentDrawer`, `AddContractModal`, `CeoApprovalModal`, `ApproveRejectModal`, `ExportEmployeeModal` |
| **TOTAL** | **Total Key UI/UX Assets Documented** | **15** | **15 Major Screens & Component Modals** |

---

## 1. Main Screens & Sub-Screens

### 1.1 Employee Directory Screen
**Trigger:** Click `People` -> `Employee Directory` in the sidebar.  
**Description:** Central workforce catalog displaying searchable employee records, KPI metrics, status filters, and quick action toolbars.

![Employee Directory Screen](../images/uiux/EmployeeDirectory.png)

---

### 1.2 Employee Profile Detail Screen
**Trigger:** Click any employee row in the Employee Directory table.  
**Description:** Dedicated 360-degree employee profile screen featuring breadcrumbs navigation, employee header banner, and consolidated tabs for Overview & Employment, Contract & Documents, Education & Certifications, and History.

![Employee Profile Detail Screen](../images/uiux/EmployeeProfileDetail.png)

---

### 1.3 Organization & Department Screen
**Trigger:** Click `People` -> `Org & Department` in the sidebar.  
**Description:** Interactive organizational structure canvas featuring department hierarchy tree, roster table view, branch filters, and zoom controls.

![Organization & Department Screen](../images/uiux/OrgDepartment.png)

---

### 1.4 Request Management Screen
**Trigger:** Click `People` -> `Request` in the sidebar.  
**Description:** Management dashboard for employee HR requests, leave approvals, status filtering, and workflow processing.

![Request Management Screen](../images/uiux/RequestManagement.png)

---

### 1.5 Create HR Request Screen
**Trigger:** Click `+ Create Request` button on the Request Management page.  
**Description:** Two-column interactive form for submitting annual leave, equipment, or policy requests with automatic quota validation.

![Create HR Request Screen](../images/uiux/CreateRequest.png)

---

### 1.6 Tracking Request Progress Screen
**Trigger:** Click `View Timeline` or any request item in the Request Management table.  
**Description:** Real-time request progress tracker displaying approval workflow steps, reviewer comments, and timeline status.

![Tracking Request Progress Screen](../images/uiux/TrackingRequest.png)

---

### 1.7 Position & Job Title Management Screen
**Trigger:** Click `Position Management` subnav link in Org & Department.  
**Description:** Management screen defining organizational job titles, competency levels, salary bands, and headcount quotas.

![Position Management Screen](../images/uiux/PositionManagement.png)

---

### 1.8 Team Management Screen
**Trigger:** Click `Team Management` subnav link in Org & Department.  
**Description:** Workspace for organizing project teams, designating team leads, and allocating member resources.

![Team Management Screen](../images/uiux/TeamManagement.png)

---

### 1.9 Reporting Lines & Hierarchy Matrix Screen
**Trigger:** Click `Reporting Lines` subnav link in Org & Department.  
**Description:** Organizational matrix displaying direct report managers, functional line supervisors, and reporting relationships.

![Reporting Lines Matrix Screen](../images/uiux/ReportingLines.png)

---

## 2. Major PopUp Modals & Drawers

### 2.1 Add New Employee Profile Modal
**Trigger:** Click `Add Employee` button on the Employee Directory toolbar.  
**Description:** Modal popup form for registering a new employee profile with personal demographics, corporate email, role, and department assignment.

![Add Employee Modal](../images/uiux/AddEmployeeModal.png)

---

### 2.2 Add Department Drawer
**Trigger:** Click `Add Department` button on the Organization & Department page header.  
**Description:** Slide-over drawer for configuring new department entities, parent division alignment, location branch, and manager assignments.

![Add Department Drawer](../images/uiux/AddDepartmentDrawer.png)

---

### 2.3 Add Labor Contract Modal
**Trigger:** Click `+ Add Contract` button on the Employee Profile Contract tab.  
**Description:** Form popup for registering official labor contracts, compensation terms, effective dates, and document attachments.

![Add Labor Contract Modal](../images/uiux/AddContractModal.png)

---

### 2.4 Director & CEO Approval Pending Modal
**Trigger:** Drag and drop any employee or department node within the Org Tree canvas.  
**Description:** Confirmation popup indicating that an organizational restructuring or employee reassignment request has been submitted for Director/CEO approval.

![CEO Approval Modal](../images/uiux/CeoApprovalModal.png)

---

### 2.5 Approve or Reject Decision Modal
**Trigger:** Click `Approve` or `Reject` on any pending request row.  
**Description:** Decision modal for approving or rejecting employee HR requests with mandatory reviewer comments.

![Approve Reject Modal](../images/uiux/ApproveRejectModal.png)

---

### 2.6 Export Employee Data Modal
**Trigger:** Click `Export CSV` / `Export Data` button on the Employee Directory toolbar.  
**Description:** Configuration modal for selecting employee data columns, date ranges, and file format export options.

![Export Employee Data Modal](../images/uiux/ExportEmployeeModal.png)
