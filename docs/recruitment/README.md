# Recruitment Documentation

## Usecase

![Recruitment Usecase](../../images/usecase/recruitment.png)

## Recruitment Sitemap

![alt text](../../images/sitemap/recruitment_sitemap.png)

## UI/UX

### Recruitment Management
![Recruitment Management](../../images/uiux/recruitment/Recruitment%20Management.png)

### Requirement Management
![Requirement Management](../../images/uiux/recruitment/Requirement%20Management.png)

### Job Management
![Job Management](../../images/uiux/recruitment/Job%20Management.png)

### Application Management
![Application Management](../../images/uiux/recruitment/Application%20Management.png)

### Interview Management
![Interview Management](../../images/uiux/recruitment/Interview%20Management.png)

### Schedule Management
![Schedule Management](../../images/uiux/recruitment/Schedule%20Management.png)

### Offer Management
![Offer Management](../../images/uiux/recruitment/Offer%20Management.png)

### Template Management
![Template Management](../../images/uiux/recruitment/Template%20Management.png)

## Database Schema (Recruitment)

### Recruitment DB Overview
![alt text](<../../images/erd/recruitment/Recruitment db.png>)

### Organization & Recruitment Requirement
![Organization & Recruitment Requirement](<../../images/erd/recruitment/Organization & Recruitment Requirement.png>)

### Job & Application
![alt text](<../../images/erd/recruitment/Job & Application.png>)

### Interview Schedule & Evaluation
![alt text](<../../images/erd/recruitment/Interview Schedule & Evaluation.png>)

### Offer & Recruitment Template
![alt text](<../../images/erd/recruitment/Offer & Recruitment Template.png>)

## Recruitment API
```text
BBV HR - Recruitment API
│
├── Requirements
│   ├── GET     /requirements
│   ├── POST    /requirements
│   ├── GET     /requirements/{requirementId}
│   ├── PATCH   /requirements/{requirementId}
│   └── DELETE  /requirements/{requirementId}
│
├── Jobs
│   ├── GET     /jobs
│   ├── POST    /jobs
│   ├── GET     /jobs/{jobId}
│   ├── PATCH   /jobs/{jobId}
│   ├── DELETE  /jobs/{jobId}
│   └── POST    /jobs/{jobId}/publish
│
├── Applications
│   ├── GET     /applications
│   ├── POST    /applications
│   ├── GET     /applications/{applicationId}
│   ├── PATCH   /applications/{applicationId}
│   ├── PATCH   /applications/{applicationId}/stage
│   └── POST    /applications/{applicationId}/reject
│
├── Schedules
│   ├── GET     /schedules
│   ├── POST    /schedules
│   ├── GET     /schedules/{scheduleId}
│   ├── PATCH   /schedules/{scheduleId}
│   └── POST    /schedules/{scheduleId}/cancel
│
├── Interviews
│   ├── GET     /interviews
│   ├── GET     /interviews/{interviewId}
│   ├── PATCH   /interviews/{interviewId}
│   ├── POST    /interviews/{interviewId}/evaluations
│   └── POST    /interviews/{interviewId}/decision
│
├── Offers
│   ├── GET     /offers
│   ├── POST    /offers
│   ├── GET     /offers/{offerId}
│   ├── PATCH   /offers/{offerId}
│   └── POST    /offers/{offerId}/send
│
└── Templates
    ├── GET     /templates
    ├── POST    /templates
    ├── GET     /templates/{templateId}
    ├── PATCH   /templates/{templateId}
    └── DELETE  /templates/{templateId}
```

## API Swagger

### Requirement & Job
![Requirement & job](../../images/api-swagger/recruitment/Requirement%20%26%20job.jpg)

### Application & Schedule
![Application & Schedule](../../images/api-swagger/recruitment/Application%20%26%20Schedule.jpg)

### Interview & Offer
![Interview & offer](../../images/api-swagger/recruitment/Interview%20%26%20offer.jpg)

### Templates
![Templates](../../images/api-swagger/recruitment/Templates.jpg)
