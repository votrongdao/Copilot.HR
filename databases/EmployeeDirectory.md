# Employee Directory Database Specification

This document defines the Entity-Relationship Diagram (ERD) and data schema for the **Employee Directory** module in Copilot.HR.

---

## 1. Entity-Relationship Diagram (ERD)

![Employee Directory](../images/databases/EmployeeDirectory.png)

---

## 2. Use Case Diagram

![Employee Directory Use Case](../images/usecase/EmployeeDirectory.png)

---

## 3. Master Schema Code (Mermaid)

```mermaid
erDiagram

    EMPLOYEE ||--|| EMPLOYEE_PROFILE : "has"
    EMPLOYEE ||--o{ CONTRACT : "owns"
    EMPLOYEE ||--o{ EDUCATION : "attained"
    EMPLOYEE ||--o{ CERTIFICATION : "holds"
    EMPLOYEE ||--o{ ASSET : "assigned"
    EMPLOYEE ||--o{ EMPLOYEE_DOCUMENT : "stores"
    EMPLOYEE ||--o{ EMPLOYEE_QUOTA : "owns"

    EMPLOYEE {
        string employee_id PK
        string email UK
        date join_date
        string employment_status
        string department_id FK
        string position_id FK
        string team_id FK
        string direct_manager_id FK
    }

    EMPLOYEE_PROFILE {
        string profile_id PK
        string employee_id FK, UK
        string first_name
        string last_name
        string phone
        string avatar_url
        string gender
        date date_of_birth
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

    EMPLOYEE_QUOTA {
        string quota_id PK
        string employee_id FK
        int year
        decimal annual_leave_total
        decimal annual_leave_used
        decimal annual_leave_remaining
        decimal sick_leave_remaining
    }
```

---

## 2. Data Dictionary

| Entity Name | Function Description | Primary Key (PK) | Foreign Keys (FK) |
| :--- | :--- | :--- | :--- |
| **`EMPLOYEE`** | Central employee system account and employment status | `employee_id` | `department_id`, `position_id`, `team_id`, `direct_manager_id` |
| **`EMPLOYEE_PROFILE`** | Personal demographic details (1:1 with `EMPLOYEE`) | `profile_id` | `employee_id` |
| **`CONTRACT`** | Labor contracts, terms, and base compensation history | `contract_id` | `employee_id` |
| **`EDUCATION`** | Academic degrees, majors, and universities | `education_id` | `employee_id` |
| **`CERTIFICATION`** | Professional credentials and international certificates | `certification_id` | `employee_id` |
| **`ASSET`** | Hardware devices assigned to personnel | `asset_id` | `employee_id` |
| **`EMPLOYEE_DOCUMENT`** | Scanned personal documents and identity files | `document_id` | `employee_id` |
| **`EMPLOYEE_QUOTA`** | Annual leave entitlement and balance tracking | `quota_id` | `employee_id` |
