# Employee Directory Database Specification

This document defines the Entity-Relationship Diagram (ERD) and data schema for the **Employee Directory & People Management** module in Copilot.HR.

---

## 1. Entity-Relationship Diagram (ERD)

```mermaid
erDiagram
    DEPARTMENT ||--o{ EMPLOYEE : "employs"
    DEPARTMENT ||--o{ DEPARTMENT : "has_parent"
    DEPARTMENT ||--o{ TEAM : "contains"
    DEPARTMENT ||--o{ POSITION : "defines"
    
    EMPLOYEE ||--o{ EMPLOYEE : "manages"
    EMPLOYEE ||--o{ CONTRACT : "owns"
    EMPLOYEE ||--o{ EDUCATION : "attained"
    EMPLOYEE ||--o{ CERTIFICATION : "holds"
    EMPLOYEE ||--o{ ASSET : "assigned"
    EMPLOYEE ||--o{ EMPLOYEE_DOCUMENT : "stores"
    EMPLOYEE ||--o{ HR_REQUEST : "submits"
    EMPLOYEE ||--o{ HR_REQUEST : "handovers_to"
    EMPLOYEE ||--o{ REPORTING_LINE : "reports_via"

    TEAM ||--o{ EMPLOYEE : "allocates"
    TEAM }|--|| EMPLOYEE : "led_by"
    DEPARTMENT }|--|| EMPLOYEE : "headed_by"
    POSITION ||--o{ EMPLOYEE : "assigned_to"

    EMPLOYEE {
        string employee_id PK
        string first_name
        string last_name
        string email UK
        string phone
        string avatar_url
        string gender
        date date_of_birth
        date join_date
        string employment_status
        string department_id FK
        string position_id FK
        string team_id FK
        string direct_manager_id FK
    }

    DEPARTMENT {
        string department_id PK
        string name
        string code UK
        string parent_department_id FK
        string department_lead_id FK
        string location_branch
        int budget_headcount
    }

    TEAM {
        string team_id PK
        string name
        string department_id FK
        string team_lead_id FK
        string description
    }

    POSITION {
        string position_id PK
        string title
        string job_level
        decimal min_salary
        decimal max_salary
        string department_id FK
    }

    CONTRACT {
        string contract_id PK
        string employee_id FK
        string contract_number UK
        string contract_type
        date start_date
        date end_date
        decimal base_salary
        string status
        string signed_document_url
    }

    EDUCATION {
        string education_id PK
        string employee_id FK
        string degree
        string institution
        string field_of_study
        int start_year
        int end_year
    }

    CERTIFICATION {
        string certification_id PK
        string employee_id FK
        string name
        string issuing_organization
        date issue_date
        date expiry_date
        string credential_id
    }

    ASSET {
        string asset_id PK
        string employee_id FK
        string asset_name
        string serial_number UK
        string asset_type
        date issue_date
        string status
    }

    EMPLOYEE_DOCUMENT {
        string document_id PK
        string employee_id FK
        string document_name
        string document_type
        string file_url
        timestamp uploaded_at
    }

    HR_REQUEST {
        string request_id PK
        string employee_id FK
        string request_type
        string priority
        string status
        string handover_employee_id FK
        timestamp submitted_at
        text reason
    }

    REPORTING_LINE {
        string reporting_id PK
        string employee_id FK
        string manager_id FK
        string reporting_type
    }
```

---

## 2. Data Dictionary Summary

### 📌 Entities & Relationships Table

| Entity Name | Function Description | Foreign Keys (FK) |
| :--- | :--- | :--- |
| **`EMPLOYEE`** | Central employee profile identity and personal status | `department_id`, `position_id`, `team_id`, `direct_manager_id` |
| **`DEPARTMENT`** | Operational department units and branch locations | `parent_department_id`, `department_lead_id` |
| **`TEAM`** | Project and functional teams under departments | `department_id`, `team_lead_id` |
| **`POSITION`** | Job titles, competency levels, and salary bands | `department_id` |
| **`CONTRACT`** | Labor contracts, terms, and base compensation history | `employee_id` |
| **`EDUCATION`** | Academic degrees, majors, and universities | `employee_id` |
| **`CERTIFICATION`** | Professional credentials and international certificates | `employee_id` |
| **`ASSET`** | Hardware devices assigned to personnel | `employee_id` |
| **`EMPLOYEE_DOCUMENT`** | Scanned personal documents and identity files | `employee_id` |
| **`HR_REQUEST`** | Internal employee requests (Leave, Equipment, OT) | `employee_id`, `handover_employee_id` |
| **`REPORTING_LINE`** | Direct and matrix reporting lines management | `employee_id`, `manager_id` |
