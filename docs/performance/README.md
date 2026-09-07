# Performance Management

## I. Use Cases

### 1. Goal Management
![Goal Management Use Case](../../images/usecase/performance/GoalManagement.png)

### 2. Performance Review
![Performance Review Use Case](../../images/usecase/performance/PerformanceReview.png)

### 3. 1-on-1 Coaching
![1-on-1 Coaching Use Case](../../images/usecase/performance/1on1coaching.png)

---

## II. UI/UX

### 1. Performance Dashboard
![Performance Dashboard](../../images/uiux/performance/dashboard.png)

### 2. Goal Directory
![Goal Directory](../../images/uiux/performance/goal.png)

### 3. Goal Detail
![Goal Detail](../../images/uiux/performance/goaldetail.png)

### 4. Self Assessment
![Self Assessment](../../images/uiux/performance/self-assesmented.png)

### 5. 360 Feedback Portal
![360 Feedback Portal](../../images/uiux/performance/360feedback.png)

### 6. Manager Evaluation
![Manager Evaluation](../../images/uiux/performance/managereval.png)

---

## III. Sitemap

### Performance Review Sitemap
![Performance Review Sitemap](../../images/sitemap/performancereview.png)

---

## IV. ERD (DB, Entity Diagram)

### 1. Performance Review ERD
![Performance Review ERD](../../images/erd/performance/performance_review.png)

### 2. 1-on-1 Coaching ERD
![1-on-1 Coaching ERD](../../images/erd/performance/1on1.png)

---

## V. API Swagger

### 1. Performance Review API Swagger
![Performance Review API Swagger](../../images/api-swagger/payroll/performance/performancereview.png)

### 2. 1-on-1 Coaching API Swagger
![1-on-1 Coaching API Swagger](../../images/api-swagger/payroll/performance/1on1.png)

---

## VI. API docs

### Performance Management

```text
Performance
└── Performance Management
    ├── Goal Management
    │   ├── Goal Cycles
    │   │   ├── GET    /goal-cycles
    │   │   ├── POST   /goal-cycles
    │   │   └── GET    /goal-cycles/{cycleId}
    │   ├── Performance Goals
    │   │   ├── GET    /goals
    │   │   ├── POST   /goals
    │   │   ├── GET    /goals/{goalId}
    │   │   ├── PUT    /goals/{goalId}
    │   │   └── PATCH  /goals/{goalId}/progress
    │   └── Goal Workflow & History
    │       ├── POST   /goals/{goalId}/approvals
    │       ├── POST   /goals/{goalId}/revisions
    │       └── GET    /goals/{goalId}/history
    ├── Performance Review Management
    │   ├── Review Cycles & Templates
    │   │   ├── GET    /review-cycles
    │   │   ├── POST   /review-cycles
    │   │   ├── GET    /review-cycles/{cycleId}
    │   │   ├── GET    /review-templates
    │   │   ├── POST   /review-templates
    │   │   └── GET    /review-templates/{templateId}/sections
    │   ├── Performance Reviews & Feedback
    │   │   ├── GET    /performance-reviews
    │   │   ├── POST   /performance-reviews
    │   │   ├── GET    /performance-reviews/{reviewId}
    │   │   ├── PATCH  /performance-reviews/{reviewId}/status
    │   │   ├── GET    /performance-reviews/{reviewId}/participants
    │   │   └── POST   /performance-reviews/{reviewId}/feedback
    │   └── Approvals & Corrections
    │       ├── POST   /performance-reviews/{reviewId}/approvals
    │       ├── POST   /performance-reviews/{reviewId}/corrections
    │       └── GET    /performance-reviews/{reviewId}/history
    ├── One-on-One Coaching Management
    │   ├── Coaching Templates & Sessions
    │   │   ├── GET    /coaching-templates
    │   │   ├── POST   /coaching-templates
    │   │   ├── GET    /one-on-ones
    │   │   ├── POST   /one-on-ones
    │   │   ├── GET    /one-on-ones/{sessionId}
    │   │   └── PATCH  /one-on-ones/{sessionId}/status
    │   └── Session Artifacts & Follow-ups
    │       ├── POST   /one-on-ones/{sessionId}/agenda-items
    │       ├── POST   /one-on-ones/{sessionId}/notes
    │       ├── POST   /one-on-ones/{sessionId}/action-items
    │       └── GET    /one-on-ones/{sessionId}/history
    └── Performance Reporting & Summaries
        └── Performance Summaries
            ├── GET    /employees/{employeeId}/performance-summary
            └── POST   /employees/{employeeId}/performance-summary/recalculate
```
