# Request Management Database Specification

This document defines the Entity-Relationship Diagram (ERD) and data schema for the **Employee Request Management & Approval Workflow** module in Copilot.HR.

---

## 1. Entity-Relationship Diagram (ERD)

```mermaid
erDiagram
    REQUEST_TYPE ||--o{ HR_REQUEST : "categorizes"
    EMPLOYEE ||--o{ HR_REQUEST : "submits"
    EMPLOYEE ||--o{ HR_REQUEST : "handover_assignee"
    EMPLOYEE ||--o{ EMPLOYEE_QUOTA : "owns"

    HR_REQUEST ||--o{ WORKFLOW_STEP : "executes_in"
    HR_REQUEST ||--o{ APPROVAL_LOG : "tracks"
    HR_REQUEST ||--o{ REQUEST_ATTACHMENT : "includes"
    HR_REQUEST ||--o{ HANDOVER_TASK : "assigns"

    EMPLOYEE ||--o{ APPROVAL_LOG : "approves_or_rejects"
    WORKFLOW_STEP ||--o{ APPROVAL_LOG : "defines_stage"

    REQUEST_TYPE {
        string type_id PK
        string type_name UK
        int default_sla_hours
        boolean requires_handover
        boolean requires_attachment
    }

    HR_REQUEST {
        string request_id PK
        string employee_id FK
        string type_id FK
        string priority
        string status
        string handover_employee_id FK
        timestamp submitted_at
        date start_date
        date end_date
        decimal duration_days
        text reason
    }

    WORKFLOW_STEP {
        string step_id PK
        string type_id FK
        int step_order
        string step_name
        string approver_role
    }

    APPROVAL_LOG {
        string log_id PK
        string request_id FK
        string step_id FK
        string approver_id FK
        string action
        text comment
        timestamp timestamp
        int remaining_sla_minutes
    }

    REQUEST_ATTACHMENT {
        string attachment_id PK
        string request_id FK
        string file_name
        string file_url
        int file_size_bytes
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

    HANDOVER_TASK {
        string task_id PK
        string request_id FK
        string task_title
        string handover_notes
        string status
    }

    EMPLOYEE {
        string employee_id PK
        string first_name
        string last_name
        string email UK
    }
```

---

## 2. Data Dictionary

### 📌 HR_REQUEST
- `request_id` (PK, VARCHAR): Unique ticket ID (e.g., REQ-1092).
- `employee_id` (FK, VARCHAR): Requester employee.
- `type_id` (FK, VARCHAR): FK to `REQUEST_TYPE`.
- `priority` (VARCHAR): Low, Medium, High.
- `status` (VARCHAR): Pending, Approved, Rejected, Cancelled.
- `duration_days` (DECIMAL): Auto-calculated business days requested.

### 📌 APPROVAL_LOG
- `log_id` (PK, VARCHAR): Log record identifier.
- `request_id` (FK, VARCHAR): Associated request.
- `approver_id` (FK, VARCHAR): Employee acting as approver.
- `action` (VARCHAR): Approved, Rejected, Reminder_Sent.
- `comment` (TEXT): Approval notes or rejection justification.
