

### 3. Core ERD Appllication Management
```mermaid
erDiagram

    CANDIDATE {
        bigint candidate_id PK
        varchar full_name
        varchar email
    }

    POSITION {
        bigint position_id PK
        varchar position_name
    }

    APPLICATION_STAGE {
        bigint application_stage_id PK
        varchar stage_name
        int stage_order
    }

    USER {
        bigint user_id PK
        varchar full_name
    }

    APPLICATION {
        bigint application_id PK
        bigint candidate_id FK
        bigint position_id FK
        bigint application_stage_id FK
        bigint owner_user_id FK
        varchar status
    }

    INTERVIEW {
        bigint interview_id PK
        bigint application_id FK
        bigint interviewer_user_id FK
        datetime scheduled_at
        varchar status
    }

    APPLICATION_EVALUATION {
        bigint evaluation_id PK
        bigint application_id FK
        bigint evaluator_user_id FK
        varchar recommendation
    }


    CANDIDATE ||--o{ APPLICATION : submits

    POSITION ||--o{ APPLICATION : for_position

    APPLICATION_STAGE ||--o{ APPLICATION : current_stage

    USER ||--o{ APPLICATION : owns


    APPLICATION ||--o{ INTERVIEW : has

    USER ||--o{ INTERVIEW : conducts


    APPLICATION ||--o{ APPLICATION_EVALUATION : receives

    USER ||--o{ APPLICATION_EVALUATION : evaluates
```

### 4. Core ERD Offer Management
```mermaid
erDiagram

    APPLICATION {
        bigint application_id PK
        bigint candidate_id FK
        bigint position_id FK
        varchar status
    }

    OFFER {
        bigint offer_id PK
        bigint application_id FK
        bigint offer_template_id FK
        bigint email_template_id FK
        varchar status
        date response_deadline
    }

    OFFER_RESPONSE {
        bigint offer_response_id PK
        bigint offer_id FK
        varchar response_type
    }

    OFFER_TEMPLATE {
        bigint offer_template_id PK
        varchar template_name
    }

    EMAIL_TEMPLATE {
        bigint email_template_id PK
        varchar template_name
    }

    APPLICATION ||--o{ OFFER : produces

    OFFER ||--o{ OFFER_RESPONSE : receives

    OFFER_TEMPLATE ||--o{ OFFER : used_by

    EMAIL_TEMPLATE ||--o{ OFFER : used_by
```

### 5. Core ERD Intake Review
```mermaid
erDiagram

    OFFER {
        bigint offer_id PK
    }

    USER {
        bigint user_id PK
    }

    ONBOARDING_SUBMISSION {
        bigint submission_id PK
        bigint offer_id FK
        varchar status
        datetime submitted_at
    }

    SUBMISSION_DOCUMENT {
        bigint document_id PK
        bigint submission_id FK
        varchar document_type
        varchar status
    }

    FIELD_MAPPING {
        bigint field_mapping_id PK
        bigint submission_id FK
        varchar source_field
        varchar target_field
        decimal confidence_score
    }

    INTAKE_REVIEW {
        bigint intake_review_id PK
        bigint submission_id FK
        bigint reviewer_user_id FK
        varchar status
    }


    OFFER ||--o| ONBOARDING_SUBMISSION : receives

    ONBOARDING_SUBMISSION ||--o{ SUBMISSION_DOCUMENT : contains

    ONBOARDING_SUBMISSION ||--o{ FIELD_MAPPING : maps

    ONBOARDING_SUBMISSION ||--o{ INTAKE_REVIEW : reviewed_by

    USER ||--o{ INTAKE_REVIEW : performs
```

### 6. Core ERD Onboarding Board 
```mermaid
erDiagram

    OFFER {
        bigint offer_id PK
        date final_start_date
    }

    USER {
        bigint user_id PK
    }

    EMPLOYEE {
        bigint employee_id PK
        bigint manager_user_id FK
    }

    ONBOARDING_STAGE {
        bigint onboarding_stage_id PK
        varchar stage_name
    }

    ONBOARDING_CASE {
        bigint onboarding_case_id PK
        bigint offer_id FK
        bigint employee_id FK
        bigint onboarding_stage_id FK
    }

    ONBOARDING_TASK {
        bigint onboarding_task_id PK
        bigint onboarding_case_id FK
        varchar status
    }

    READINESS_CHECKLIST_ITEM {
        bigint readiness_item_id PK
        bigint onboarding_case_id FK
        varchar status
    }

    ONBOARDING_BLOCKER {
        bigint blocker_id PK
        bigint onboarding_case_id FK
        varchar status
    }


    USER ||--o{ EMPLOYEE : manages

    EMPLOYEE ||--o{ ONBOARDING_CASE : has

    OFFER ||--o| ONBOARDING_CASE : starts

    ONBOARDING_STAGE ||--o{ ONBOARDING_CASE : current_stage

    ONBOARDING_CASE ||--o{ ONBOARDING_TASK : contains

    ONBOARDING_CASE ||--o{ READINESS_CHECKLIST_ITEM : checks

    ONBOARDING_CASE ||--o{ ONBOARDING_BLOCKER : has
```

### 7. Core ERD Assigned Task by Role
```mermaid
erDiagram

    EMPLOYEE {
        bigint employee_id PK
    }

    ONBOARDING_CASE {
        bigint onboarding_case_id PK
        bigint employee_id FK
    }

    TASK_TEMPLATE {
        bigint task_template_id PK
        varchar task_name
    }

    ONBOARDING_TASK {
        bigint onboarding_task_id PK
        bigint onboarding_case_id FK
        bigint task_template_id FK
        varchar task_name
        varchar status
    }

    TEAM {
        bigint team_id PK
        varchar team_name
    }

    USER {
        bigint user_id PK
        bigint team_id FK
    }

    ROLE {
        bigint role_id PK
        varchar role_name
    }

    USER_ROLE {
        bigint user_id FK
        bigint role_id FK
    }

    TASK_ASSIGNMENT {
        bigint task_assignment_id PK
        bigint onboarding_task_id FK
        bigint assigned_user_id FK
    }


    EMPLOYEE ||--o{ ONBOARDING_CASE : has

    ONBOARDING_CASE ||--o{ ONBOARDING_TASK : contains

    TASK_TEMPLATE o|--o{ ONBOARDING_TASK : generates

    ONBOARDING_TASK ||--o{ TASK_ASSIGNMENT : has

    USER ||--o{ TASK_ASSIGNMENT : assigned_to

    TEAM ||--o{ USER : contains

    USER ||--o{ USER_ROLE : has

    ROLE ||--o{ USER_ROLE : assigned
```

### 8. Core ERD Tracking Progress 
```mermaid
erDiagram
    direction LR

    USER {
        bigint user_id PK
    }

    EMPLOYEE {
        bigint employee_id PK
        bigint manager_user_id FK
    }

    ONBOARDING_CASE {
        bigint onboarding_case_id PK
        bigint employee_id FK
    }

    PROBATION {
        bigint probation_id PK
        bigint onboarding_case_id FK
        date start_date
        date end_date
        varchar status
    }

    SELF_REVIEW {
        bigint self_review_id PK
        bigint probation_id FK
        varchar status
    }

    EVALUATION {
        bigint evaluation_id PK
        bigint probation_id FK
        datetime scheduled_at
        varchar status
        varchar result
    }

    EVALUATION_REVIEWER {
        bigint evaluation_id FK
        bigint reviewer_user_id FK
        varchar recommendation
    }


    USER ||--o{ EMPLOYEE : manages

    EMPLOYEE ||--o{ ONBOARDING_CASE : has

    ONBOARDING_CASE ||--o| PROBATION : enters

    PROBATION ||--o{ SELF_REVIEW : has

    PROBATION ||--o{ EVALUATION : has

    EVALUATION ||--o{ EVALUATION_REVIEWER : reviewers

    USER ||--o{ EVALUATION_REVIEWER : reviews
```