# Copilot.HR - Database Architecture & Master ERD

**Total Database Tables**: **18 Tables**

---

## 1. Master Table Relationships Matrix

| # | Table Name | Description | Primary Key (PK) | Foreign Keys (FK) | Related Table | Cardinality |
| :---: | :--- | :--- | :--- | :--- | :--- | :--- |
| **1** | **`COMPANY_BRANCH`** | Office locations and regional hubs | `branch_id` | *None* | `DEPARTMENT` | 1:N |
| **2** | **`DEPARTMENT`** | Operational units & hierarchy | `department_id` | `parent_department_id`<br>`department_lead_id`<br>`branch_id` | `DEPARTMENT`<br>`EMPLOYEE`<br>`COMPANY_BRANCH` | N:1 (Self)<br>1:1<br>N:1 |
| **3** | **`TEAM`** | Cross-functional project teams | `team_id` | `department_id`<br>`team_lead_id` | `DEPARTMENT`<br>`EMPLOYEE` | N:1<br>1:1 |
| **4** | **`POSITION`** | Job titles & salary bands | `position_id` | `department_id` | `DEPARTMENT` | N:1 |
| **5** | **`EMPLOYEE`** | Central employee profile records | `employee_id` | `department_id`<br>`position_id`<br>`team_id`<br>`direct_manager_id` | `DEPARTMENT`<br>`POSITION`<br>`TEAM`<br>`EMPLOYEE` | N:1<br>N:1<br>N:1<br>N:1 (Self) |
| **6** | **`CONTRACT`** | Labor contracts & base pay | `contract_id` | `employee_id` | `EMPLOYEE` | N:1 |
| **7** | **`EDUCATION`** | Academic degrees & universities | `education_id` | `employee_id` | `EMPLOYEE` | N:1 |
| **8** | **`CERTIFICATION`** | Professional certifications | `certification_id` | `employee_id` | `EMPLOYEE` | N:1 |
| **9** | **`ASSET`** | Hardware devices assigned to staff | `asset_id` | `employee_id` | `EMPLOYEE` | N:1 |
| **10** | **`EMPLOYEE_DOCUMENT`** | Scanned identity & HR files | `document_id` | `employee_id` | `EMPLOYEE` | N:1 |
| **11** | **`REQUEST_TYPE`** | Categories & default SLA rules | `type_id` | *None* | `HR_REQUEST`<br>`WORKFLOW_STEP` | 1:N<br>1:N |
| **12** | **`HR_REQUEST`** | Employee ticket applications | `request_id` | `employee_id`<br>`type_id`<br>`handover_employee_id` | `EMPLOYEE`<br>`REQUEST_TYPE`<br>`EMPLOYEE` | N:1<br>N:1<br>N:1 |
| **13** | **`WORKFLOW_STEP`** | Multi-stage approval sequence | `step_id` | `type_id` | `REQUEST_TYPE` | N:1 |
| **14** | **`APPROVAL_LOG`** | Audit trail of manager approvals | `log_id` | `request_id`<br>`step_id`<br>`approver_id` | `HR_REQUEST`<br>`WORKFLOW_STEP`<br>`EMPLOYEE` | N:1<br>N:1<br>N:1 |
| **15** | **`REQUEST_ATTACHMENT`** | Supporting files for requests | `attachment_id` | `request_id` | `HR_REQUEST` | N:1 |
| **16** | **`EMPLOYEE_QUOTA`** | Annual leave & quota balance | `quota_id` | `employee_id` | `EMPLOYEE` | N:1 |
| **17** | **`HANDOVER_TASK`** | Work handover checklist items | `task_id` | `request_id` | `HR_REQUEST` | N:1 |
| **18** | **`REPORTING_LINE`** | Direct & Matrix reporting lines | `reporting_id` | `employee_id`<br>`manager_id` | `EMPLOYEE`<br>`EMPLOYEE` | N:1<br>N:1 |

---

## 2. Master System ERD Diagram

```mermaid
erDiagram
    COMPANY_BRANCH ||--o{ DEPARTMENT : "houses"
    DEPARTMENT ||--o{ DEPARTMENT : "parent_of"
    DEPARTMENT ||--o{ TEAM : "contains"
    DEPARTMENT ||--o{ POSITION : "defines"
    DEPARTMENT ||--o{ EMPLOYEE : "employs"
    
    TEAM ||--o{ EMPLOYEE : "allocates"
    POSITION ||--o{ EMPLOYEE : "assigned_to"

    EMPLOYEE ||--o{ EMPLOYEE : "manages"
    EMPLOYEE ||--o{ CONTRACT : "owns"
    EMPLOYEE ||--o{ EDUCATION : "attained"
    EMPLOYEE ||--o{ CERTIFICATION : "holds"
    EMPLOYEE ||--o{ ASSET : "assigned"
    EMPLOYEE ||--o{ EMPLOYEE_DOCUMENT : "stores"
    EMPLOYEE ||--o{ HR_REQUEST : "submits"
    EMPLOYEE ||--o{ HR_REQUEST : "handover_assignee"
    EMPLOYEE ||--o{ EMPLOYEE_QUOTA : "owns"
    EMPLOYEE ||--o{ APPROVAL_LOG : "approves_or_rejects"
    EMPLOYEE ||--o{ REPORTING_LINE : "reports_via"

    REQUEST_TYPE ||--o{ HR_REQUEST : "categorizes"
    HR_REQUEST ||--o{ WORKFLOW_STEP : "executes_in"
    HR_REQUEST ||--o{ APPROVAL_LOG : "tracks"
    HR_REQUEST ||--o{ REQUEST_ATTACHMENT : "includes"
    HR_REQUEST ||--o{ HANDOVER_TASK : "assigns"

    COMPANY_BRANCH {
        string branch_id PK
        string branch_name
        string branch_code UK
        string city
    }

    DEPARTMENT {
        string department_id PK
        string name
        string code UK
        string parent_department_id FK
        string department_lead_id FK
        string branch_id FK
        int budget_headcount
    }

    TEAM {
        string team_id PK
        string name
        string department_id FK
        string team_lead_id FK
    }

    POSITION {
        string position_id PK
        string title
        string job_level
        decimal min_salary
        decimal max_salary
    }

    EMPLOYEE {
        string employee_id PK
        string first_name
        string last_name
        string email UK
        string department_id FK
        string position_id FK
        string team_id FK
        string direct_manager_id FK
    }

    CONTRACT {
        string contract_id PK
        string employee_id FK
        string contract_number UK
        date start_date
        decimal base_salary
    }

    EDUCATION {
        string education_id PK
        string employee_id FK
        string degree
        string institution
        int end_year
    }

    CERTIFICATION {
        string certification_id PK
        string employee_id FK
        string name
        string issuing_organization
        date issue_date
    }

    ASSET {
        string asset_id PK
        string employee_id FK
        string asset_name
        string serial_number UK
        string status
    }

    EMPLOYEE_DOCUMENT {
        string document_id PK
        string employee_id FK
        string document_name
        string file_url
        timestamp uploaded_at
    }

    REQUEST_TYPE {
        string type_id PK
        string type_name UK
        int default_sla_hours
    }

    HR_REQUEST {
        string request_id PK
        string employee_id FK
        string type_id FK
        string priority
        string status
        string handover_employee_id FK
        timestamp submitted_at
    }

    WORKFLOW_STEP {
        string step_id PK
        string type_id FK
        int step_order
        string step_name
    }

    APPROVAL_LOG {
        string log_id PK
        string request_id FK
        string step_id FK
        string approver_id FK
        string action
        timestamp timestamp
    }

    REQUEST_ATTACHMENT {
        string attachment_id PK
        string request_id FK
        string file_name
        string file_url
    }

    EMPLOYEE_QUOTA {
        string quota_id PK
        string employee_id FK
        decimal annual_leave_remaining
    }

    HANDOVER_TASK {
        string task_id PK
        string request_id FK
        string task_title
        string status
    }

    REPORTING_LINE {
        string reporting_id PK
        string employee_id FK
        string manager_id FK
        string reporting_type
    }
```
