# Onboarding Documents

## Onboarding Sitemap 
![alt text](../../images/sitemap/onboarding-sitemap.png)

## Onboarding Use Case
![Onboarding Use Case](../../images/usecase/onboard-usecase.png)

## UI/UX and Database

### 1. Application Management
![Application Management UI](../../images/uiux/onboard/application-screen.png)
![Application Management ERD](../../images/erd/application-erd.png)

### 2. Offer Management
![Offer Management UI](../../images/uiux/onboard/offer-management-screen.png)
![Offer Template UI](../../images/uiux/onboard/offer-template-screen.png)
![Offer Management ERD](../../images/erd/offer-erd.png)

### 3. Contract Management
![Contract Management UI](../../images/uiux/onboard/contract-screen.png)
![Contract Management ERD](../../images/erd/contract-erd.png)

### 4. Intake Review
![Intake Review UI](../../images/uiux/onboard/intake-screen.png)
![Intake Review ERD](../../images/erd/intake-review-erd.png)

### 5. Onboarding Board 
![Onboarding Board UI](../../images/uiux/onboard/onboard-board-screen.png)
![Onboarding Board ERD](../../images/erd/board-erd.png)

### 6. Assigned Task by Role
![Assigned Task UI](../../images/uiux/onboard/assigned-task-screen.png)
![Assigned Task ERD](../../images/erd/assigned-task-erd.png)

### 7. Tracking Onboard Progress 
![Tracking Onboard Progress UI](../../images/uiux/onboard/tracking-screen.png)
![Tracking Onboard Progress ERD](../../images/erd/tracking-erd.png)

## API route tree structure
https://app.swaggerhub.com/apis/bbv-a74/Onboarding/1.0.0#/
```text
Copilot HR - Employee Onboarding API
│
├── Application Management
│   ├── GET    /applications
│   ├── POST   /applications
│   ├── GET    /applications/{applicationId}
│   ├── PATCH  /applications/{applicationId}/stage
│   ├── GET    /applications/{applicationId}/interviews
│   ├── POST   /applications/{applicationId}/interviews
│   ├── GET    /applications/{applicationId}/evaluations
│   └── POST   /applications/{applicationId}/evaluations
│
├── Offer Management
│   ├── GET    /candidate-form-templates
│   ├── POST   /offers
│   ├── GET    /offers/{offerId}
│   ├── POST   /offers/{offerId}/send
│   └── POST   /offers/{offerId}/respond
│
├── Intake Review & Document Submission
│   ├── POST   /onboarding/submissions
│   ├── GET    /onboarding/submissions/{submissionId}
│   ├── POST   /onboarding/submissions/{submissionId}/documents
│   ├── POST   /onboarding/submissions/{submissionId}/field-mappings/parse
│   ├── GET    /onboarding/submissions/{submissionId}/generated-outputs
│   └── POST   /onboarding/submissions/{submissionId}/reviews
│
├── Onboarding Board & Case Management
│   ├── POST   /onboarding/cases
│   ├── GET    /onboarding/cases
│   ├── GET    /onboarding/cases/{caseId}
│   ├── PATCH  /onboarding/cases/{caseId}/stage
│   ├── GET    /onboarding/cases/{caseId}/readiness-checklist
│   ├── PATCH  /onboarding/cases/{caseId}/readiness-checklist/{itemId}
│   ├── GET    /onboarding/cases/{caseId}/blockers
│   └── POST   /onboarding/cases/{caseId}/blockers
│
├── Task Assignment & Workflows
│   ├── GET    /onboarding/cases/{caseId}/tasks
│   ├── POST   /onboarding/cases/{caseId}/tasks
│   ├── PATCH  /onboarding/tasks/{taskId}
│   ├── POST   /onboarding/tasks/{taskId}/assignments
│   ├── GET    /onboarding/tasks/{taskId}/comments
│   └── POST   /onboarding/tasks/{taskId}/comments
│
└── Probation & Performance Tracking
    ├── POST   /onboarding/cases/{caseId}/probation
    ├── GET    /onboarding/cases/{caseId}/probation
    ├── POST   /probation/{probationId}/self-reviews
    ├── POST   /probation/{probationId}/evaluations
    ├── POST   /probation/evaluations/{evaluationId}/reviewers
    └── POST   /probation/evaluations/{evaluationId}/finalize
```