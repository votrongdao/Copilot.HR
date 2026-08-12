# Integration Management

## Use Case
![Integration Use Case](../../images/usecase/Integration-usecase.png)

## Sitemap
![Integration Sitemap](../../images/sitemap/integration.png)

## System ERD
![Integration ERD](../../images/erd/integration.png)

## UI/UX Designs and Entity Relationship Diagrams

### Integration Overview

![Integration](<../../images/uiux/integration/Integration.png>)

```mermaid
erDiagram
    COMPANY ||--o{ INTEGRATION : "enables"
    
    COMPANY {
        string id PK
        string name
    }

    INTEGRATION {
        string id PK
        string company_id FK
        string provider_name "e.g., Google, Microsoft, LinkedIn"
        string category "Calendar, Email, Recruitment"
        string status "Connected, Disconnected"
        datetime connected_at
    }
```

### Calendar Integration

![Calendar Integration](<../../images/uiux/integration/Calendar%20Integration.png>)

```mermaid
erDiagram
    EMPLOYEE ||--o{ CALENDAR_CONNECTION : "authorizes"
    CALENDAR_CONNECTION ||--o{ EVENT_SYNC : "manages"
    
    EMPLOYEE {
        string id PK
        string name
    }
    
    CALENDAR_CONNECTION {
        string id PK
        string employee_id FK
        string provider "Google Calendar, Outlook"
        string account_email
        string sync_status
    }
    
    EVENT_SYNC {
        string id PK
        string connection_id FK
        string event_id "External Event ID"
        string title
        datetime start_time
        datetime end_time
    }
```

### Email Integration

![Email Integration](<../../images/uiux/integration/Email%20Integration.png>)

```mermaid
erDiagram
    EMPLOYEE ||--o{ EMAIL_CONNECTION : "authorizes"
    EMAIL_CONNECTION ||--o{ EMAIL_SYNC_LOG : "generates"
    
    EMPLOYEE {
        string id PK
        string name
        string primary_email
    }
    
    EMAIL_CONNECTION {
        string id PK
        string employee_id FK
        string provider "Gmail, Outlook"
        string email_address
        string status
    }
    
    EMAIL_SYNC_LOG {
        string id PK
        string connection_id FK
        string message_id
        string subject
        datetime synced_at
        string status "Success, Failed"
    }
```

### Recruitment Integration

![Recruitment Integration](<../../images/uiux/integration/Recruiment%20Integration.png>)

```mermaid
erDiagram
    INTEGRATION ||--o{ JOB_POSTING_SYNC : "publishes"
    JOB_POSTING_SYNC ||--o{ CANDIDATE_IMPORT : "receives"
    
    INTEGRATION {
        string id PK
        string provider_name "e.g., LinkedIn, Indeed"
        string status
    }
    
    JOB_POSTING_SYNC {
        string id PK
        string integration_id FK
        string internal_job_id
        string external_job_id
        string status "Active, Closed"
        datetime posted_at
    }
    
    CANDIDATE_IMPORT {
        string id PK
        string job_sync_id FK
        string external_candidate_id
        string name
        string email
        string resume_url
        datetime imported_at
    }
```

### Sync History

![Sync History](<../../images/uiux/integration/Sync%20History.png>)

```mermaid
erDiagram
    INTEGRATION ||--o{ SYNC_HISTORY : "logs"
    
    INTEGRATION {
        string id PK
        string provider_name
        string category
    }
    
    SYNC_HISTORY {
        string id PK
        string integration_id FK
        string sync_type "Manual, Scheduled, Webhook"
        string status "Success, Error, In Progress"
        int records_processed
        int error_count
        datetime started_at
        datetime completed_at
        string error_details
    }
```

### Full Integration ERD

```mermaid
erDiagram
    COMPANY ||--o{ INTEGRATION : "enables"
    EMPLOYEE ||--o{ CALENDAR_CONNECTION : "authorizes"
    EMPLOYEE ||--o{ EMAIL_CONNECTION : "authorizes"
    INTEGRATION ||--o{ JOB_POSTING_SYNC : "publishes"
    INTEGRATION ||--o{ SYNC_HISTORY : "logs"
    CALENDAR_CONNECTION ||--o{ EVENT_SYNC : "manages"
    EMAIL_CONNECTION ||--o{ EMAIL_SYNC_LOG : "generates"
    JOB_POSTING_SYNC ||--o{ CANDIDATE_IMPORT : "receives"
    
    COMPANY {
        string id PK
        string name
    }

    EMPLOYEE {
        string id PK
        string name
    }
    
    INTEGRATION {
        string id PK
        string company_id FK
        string provider_name
        string category
        string status
    }
    
    CALENDAR_CONNECTION {
        string id PK
        string employee_id FK
        string provider
        string account_email
        string sync_status
    }
    
    EVENT_SYNC {
        string id PK
        string connection_id FK
        string event_id
        string title
        datetime start_time
    }
    
    EMAIL_CONNECTION {
        string id PK
        string employee_id FK
        string provider
        string email_address
        string status
    }
    
    EMAIL_SYNC_LOG {
        string id PK
        string connection_id FK
        string message_id
        string subject
        string status
    }
    
    JOB_POSTING_SYNC {
        string id PK
        string integration_id FK
        string internal_job_id
        string external_job_id
        string status
    }
    
    CANDIDATE_IMPORT {
        string id PK
        string job_sync_id FK
        string external_candidate_id
        string name
        string email
    }
    
    SYNC_HISTORY {
        string id PK
        string integration_id FK
        string sync_type
        string status
        int records_processed
        datetime started_at
    }
```

## API Documentation

```text
BBV HR - Integration Management API
│
├── Integrations
│   ├── GET     /integrations
│   ├── POST    /integrations
│   ├── GET     /integrations/{integrationId}
│   ├── PUT     /integrations/{integrationId}
│   ├── DELETE  /integrations/{integrationId}
│   ├── POST    /integrations/{integrationId}/test
│   └── POST    /integrations/{integrationId}/reconnect
│
├── Calendar Integration
│   ├── GET     /integrations/{integrationId}/calendar-config
│   ├── PUT     /integrations/{integrationId}/calendar-config
│   └── GET     /integrations/{integrationId}/calendar-events
│
├── Email Integration
│   ├── GET     /integrations/{integrationId}/email-config
│   ├── PUT     /integrations/{integrationId}/email-config
│   ├── GET     /integrations/{integrationId}/email-templates
│   ├── POST    /integrations/{integrationId}/email-templates
│   ├── PUT     /email-templates/{templateId}
│   ├── DELETE  /email-templates/{templateId}
│   ├── GET     /integrations/{integrationId}/email-logs
│   └── POST    /integrations/{integrationId}/send-test-email
│
├── Recruitment Integration
│   ├── GET     /integrations/{integrationId}/recruitment-config
│   ├── PUT     /integrations/{integrationId}/recruitment-config
│   ├── GET     /integrations/{integrationId}/job-postings
│   ├── POST    /integrations/{integrationId}/job-postings
│   ├── POST    /job-postings/{jobPostingSyncId}/sync
│   ├── POST    /job-postings/{jobPostingSyncId}/close
│   ├── GET     /job-postings/{jobPostingSyncId}/candidate-imports
│   └── POST    /integrations/{integrationId}/application-sync
│
└── Sync History
    ├── POST    /integrations/{integrationId}/sync
    ├── GET     /sync-history
    ├── GET     /sync-history/{syncId}
    └── POST    /sync-history/{syncId}/retry
```

![Integration API](../../images/api-swagger/integration/integration.png)
![Calendar Integration API](../../images/api-swagger/integration/calendar.png)
![Email Integration API](../../images/api-swagger/integration/email.png)
![Recruitment Integration API](../../images/api-swagger/integration/recruitment.png)
![Sync History API](../../images/api-swagger/integration/sync-history.png)
