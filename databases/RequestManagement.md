# HR Request Engine Database Specification (PPT Slide 3)

This document defines the Entity-Relationship Diagram (ERD) and data schema for the **HR Request Engine & Multi-Stage Approval Workflow** module in Copilot.HR.

---

## 1. Presentation Slide ERD Diagram (Slide 3 - 6 Tables)

```mermaid
erDiagram
    REQUEST_TYPE ||--o{ HR_REQUEST : "categorizes"
    REQUEST_TYPE ||--o{ WORKFLOW_STEP : "defines_steps"
    HR_REQUEST ||--o{ APPROVAL_LOG : "tracks_audit"
    HR_REQUEST ||--o{ REQUEST_ATTACHMENT : "includes"
    HR_REQUEST ||--o{ HANDOVER_TASK : "assigns"

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

    HANDOVER_TASK {
        string task_id PK
        string request_id FK
        string task_title
        string handover_notes
        string status
    }
```

---

## 2. Data Dictionary Summary (6 Core Tables)

| Entity Name | Function Description | Primary Key (PK) | Foreign Keys (FK) |
| :--- | :--- | :--- | :--- |
| **`REQUEST_TYPE`** | Categories & SLA rules for employee requests | `type_id` | *None* |
| **`HR_REQUEST`** | Employee ticket application instances | `request_id` | `employee_id`, `type_id`, `handover_employee_id` |
| **`WORKFLOW_STEP`** | Multi-stage approval sequence definition | `step_id` | `type_id` |
| **`APPROVAL_LOG`** | Immutable audit log of manager approvals & SLA tracking | `log_id` | `request_id`, `step_id`, `approver_id` |
| **`REQUEST_ATTACHMENT`** | Supporting file attachments for requests | `attachment_id` | `request_id` |
| **`HANDOVER_TASK`** | Work handover checklist items before request approval | `task_id` | `request_id` |
