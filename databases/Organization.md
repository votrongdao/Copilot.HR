# Organization Database Specification

This document defines the Entity-Relationship Diagram (ERD) and data schema for the **Organization** module in Copilot.HR.

---

## 1. Entity-Relationship Diagram (ERD)

![Organization](../images/database/Organization.png)

```mermaid
erDiagram
    COMPANY_BRANCH ||--o{ DEPARTMENT : "houses"
    DEPARTMENT ||--o{ DEPARTMENT : "parent_of"
    DEPARTMENT ||--o{ TEAM : "contains"
    DEPARTMENT ||--o{ POSITION : "defines"
    DEPARTMENT ||--o{ EMPLOYEE : "employs"
    
    TEAM ||--o{ TEAM_MEMBER : "has_members"
    EMPLOYEE ||--o{ TEAM_MEMBER : "assigned_to"
    TEAM }|--|| EMPLOYEE : "led_by"
    DEPARTMENT }|--|| EMPLOYEE : "headed_by"
    
    POSITION ||--o{ EMPLOYEE : "assigned_to"
    EMPLOYEE ||--o{ REPORTING_LINE : "subordinate_in"
    EMPLOYEE ||--o{ REPORTING_LINE : "manager_in"

    COMPANY_BRANCH {
        string branch_id PK
        string branch_name
        string branch_code UK
        string address
        string city
        string country
        string status
    }

    DEPARTMENT {
        string department_id PK
        string name
        string code UK
        string parent_department_id FK
        string department_lead_id FK
        string branch_id FK
        int budget_headcount
        string status
    }

    TEAM {
        string team_id PK
        string name
        string department_id FK
        string team_lead_id FK
        string description
        string status
    }

    TEAM_MEMBER {
        string member_id PK
        string team_id FK
        string employee_id FK
        string role_in_team
        decimal allocation_percentage
        date joined_date
        string status
    }

    POSITION {
        string position_id PK
        string title
        string job_level
        decimal min_salary
        decimal max_salary
        string department_id FK
        int target_headcount
        string status
    }

    REPORTING_LINE {
        string reporting_id PK
        string employee_id FK
        string manager_id FK
        string reporting_type
        date effective_date
    }

    EMPLOYEE {
        string employee_id PK
        string first_name
        string last_name
        string email UK
        string department_id FK
        string position_id FK
        string team_id FK
    }
```

---

## 2. Data Dictionary

### COMPANY_BRANCH
- `branch_id` (PK, VARCHAR): Unique identifier for company branch/hub location.
- `branch_name` (VARCHAR): Name of branch (e.g., Headquarters HCMC, Hanoi Hub).
- `branch_code` (VARCHAR, UNIQUE): Short code (e.g., HQ-HCMC, HUB-HN).
- `status` (VARCHAR): Operational status (Active, Inactive).

### DEPARTMENT
- `department_id` (PK, VARCHAR): Unique department identifier.
- `name` (VARCHAR): Department title (e.g., Technology & Software).
- `parent_department_id` (FK, VARCHAR): Self-referencing FK for organizational hierarchy.
- `department_lead_id` (FK, VARCHAR): FK to `EMPLOYEE` acting as Department Head.
- `branch_id` (FK, VARCHAR): FK to `COMPANY_BRANCH`.

### TEAM
- `team_id` (PK, VARCHAR): Unique team identifier.
- `department_id` (FK, VARCHAR): Parent department.
- `team_lead_id` (FK, VARCHAR): Team lead employee.

### TEAM_MEMBER
- `member_id` (PK, VARCHAR): Unique membership assignment identifier.
- `team_id` (FK, VARCHAR): Foreign key to `TEAM`.
- `employee_id` (FK, VARCHAR): Foreign key to `EMPLOYEE`.
- `role_in_team` (VARCHAR): Member role within the team (e.g. Core Contributor, Scrum Master, Lead).
- `allocation_percentage` (DECIMAL): Capacity allocation percentage (e.g. 50%, 100%).
- `joined_date` (DATE): Date employee joined the team.
- `status` (VARCHAR): Assignment status (Active, Inactive).

### POSITION
- `position_id` (PK, VARCHAR): Position identifier.
- `job_level` (VARCHAR): Competency level (L1 to L6).
- `min_salary`, `max_salary` (DECIMAL): Salary band bounds.
