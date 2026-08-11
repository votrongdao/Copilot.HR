# Request Database Specification

This document defines the Entity-Relationship Diagram (ERD) and data schema for the **Request** module in Copilot.HR.

---

## 1. Entity-Relationship Diagram (ERD)

![Request](../images/databases/Request_Managment.png)

---

## 2. Use Case Diagram

![Request Use Case](../images/usecase/RequestManagement.png)

---

## 3. Master Schema Code (Mermaid)

```mermaid
erDiagram
    REQUEST_TYPE ||--o{ TICKET_REQUEST : "categorizes"
    REQUEST_TYPE ||--o{ WORKFLOW_STEP : "defines_steps"
    TICKET_REQUEST ||--o{ APPROVAL_LOG : "tracks_audit"
    TICKET_REQUEST ||--o{ REQUEST_ATTACHMENT : "includes"
    TICKET_REQUEST ||--o{ HANDOVER_TASK : "assigns"

    REQUEST_TYPE {
        string type_id PK
        string type_code UK
        string type_name
        string category
        string description
        int default_sla_hours
        boolean requires_handover
        boolean requires_attachment
        boolean is_active
    }

    TICKET_REQUEST {
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

## 2. Data Dictionary

| Entity Name | Function Description | Primary Key (PK) | Foreign Keys (FK) |
| :--- | :--- | :--- | :--- |
| **`REQUEST_TYPE`** | Master category rules, SLAs, and approval flags | `type_id` | *None* |
| **`TICKET_REQUEST`** | Internal ticket application instances (Self-Service & HR Ops) | `request_id` | `employee_id`, `type_id`, `handover_employee_id` |
| **`WORKFLOW_STEP`** | Multi-stage approval sequence definition | `step_id` | `type_id` |
| **`APPROVAL_LOG`** | Immutable audit log of manager approvals & SLA tracking | `log_id` | `request_id`, `step_id`, `approver_id` |
| **`REQUEST_ATTACHMENT`** | Supporting file attachments for requests | `attachment_id` | `request_id` |
| **`HANDOVER_TASK`** | Work handover checklist items before request approval | `task_id` | `request_id` |
