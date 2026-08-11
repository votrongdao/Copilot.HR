# Project Management Documentation

## Usecase

### Project Management

![alt text](../../images/usecase/project-management/Project-Management.drawio.png)

## UI/UX

### Staff

![alt text](../../images/uiux/project-management/Employee-My-Capacity.png)

![alt text](../../images/uiux/project-management/Employee-My-Productivity.png)

![alt text](../../images/uiux/project-management/Employee-My-Project.png)

![alt text](../../images/uiux/project-management/Employee-My-Time.png)

![alt text](../../images/uiux/project-management/Employee-Project-Detail.png)

### HR

![alt text](../../images/uiux/project-management/HR-Capacity-Overview.png)

![alt text](../../images/uiux/project-management/HR-Overtime-&-Training.png)

![alt text](../../images/uiux/project-management/HR-Resource-Overview.png)

![alt text](../images/uiux/project-management/HR-Utilization.png)

### Manager

![alt text](../../images/uiux/project-management/PM-Add-Project-Member-Drawer.png)

![alt text](../../images/uiux/project-management/PM-Create-Project-Form.png)

![alt text](../../images/uiux/project-management/PM-Employee-Productivity-Detail.png)

![alt text](../../images/uiux/project-management/PM-Project-Budget.png)

![alt text](../../images/uiux/project-management/PM-Project-Capacity-View.png)

![alt text](../../images/uiux/project-management/PM-Project-Detail-View.png)

![alt text](../../images/uiux/project-management/PM-Projects-Dashboard.png)

![alt text](../../images/uiux/project-management/PM-Project-Members-List.png)

![alt text](../../images/uiux/project-management/PM-Resource-Allocation.png)

![alt text](../../images/uiux/project-management/PM-Team-Productivity.png)

![alt text](../../images/uiux/project-management/PM-Time-Tracking.png)

![alt text](../../images/uiux/project-management/PM-Timesheet-Review.png)

## Database Design

### Project Setup & Management, Project Member & Allocation, Project Effort / Time Tracking

![alt text](../../images/erd/project-management/project-setup.png)

### Capability Management + Budget Management

![alt text](../../images/erd/project-management/capacity-management.png)

### Productivity Monitoring

![alt text](../../images/erd/project-management/productivity-monitoring.png)

## API Document

```md
BBV HR - Project Management API

├── MVP 1 - Core Project Management
│
│   ├── Projects
│   │   ├── GET     /projects
│   │   ├── POST    /projects
│   │   ├── GET     /projects/{projectId}
│   │   ├── PATCH   /projects/{projectId}
│   │   └── DELETE  /projects/{projectId}
│   │
│   ├── Project Members
│   │   ├── GET     /projects/{projectId}/members
│   │   ├── POST    /projects/{projectId}/members
│   │   ├── GET     /projects/{projectId}/members/{memberId}
│   │   ├── PATCH   /projects/{projectId}/members/{memberId}
│   │   ├── DELETE  /projects/{projectId}/members/{memberId}
│   │   └── PATCH   /projects/{projectId}/members/{memberId}/allocation
│   │
│   └── Project Effort
│       ├── GET     /projects/{projectId}/effort
│       ├── GET     /projects/{projectId}/effort/members
│       └── GET     /projects/{projectId}/time-entries
│
│
├── MVP 2 - Capability & Budget Management
│
│   ├── Capabilities
│   │   ├── GET     /capabilities
│   │   ├── POST    /capabilities
│   │   ├── PATCH   /capabilities/{capabilityId}
│   │   └── DELETE  /capabilities/{capabilityId}
│   │
│   ├── Employee Capabilities
│   │   ├── GET     /employees/{employeeId}/capabilities
│   │   ├── POST    /employees/{employeeId}/capabilities
│   │   ├── PATCH   /employees/{employeeId}/capabilities/{capabilityId}
│   │   └── DELETE  /employees/{employeeId}/capabilities/{capabilityId}
│   │
│   ├── Project Capability Requirements
│   │   ├── GET     /projects/{projectId}/required-capabilities
│   │   ├── POST    /projects/{projectId}/required-capabilities
│   │   ├── PATCH   /projects/{projectId}/required-capabilities/{capabilityId}
│   │   ├── DELETE  /projects/{projectId}/required-capabilities/{capabilityId}
│   │   └── GET     /projects/{projectId}/capability-gap
│   │
│   └── Project Budget
│       ├── GET     /projects/{projectId}/budget
│       ├── GET     /projects/{projectId}/budget-adjustments
│       ├── POST    /projects/{projectId}/budget-adjustments
│       ├── GET     /projects/{projectId}/budget-adjustments/{adjustmentId}
│       ├── POST    /projects/{projectId}/budget-adjustments/{adjustmentId}/approve
│       └── POST    /projects/{projectId}/budget-adjustments/{adjustmentId}/reject
│
│
└── MVP 3 - Productivity Monitoring

    ├── Project Productivity
    │   ├── GET     /projects/{projectId}/productivity
    │   └── GET     /projects/{projectId}/productivity/sessions
    │
    ├── Productivity Sessions
    │   ├── POST    /productivity/sessions
    │   ├── GET     /productivity/sessions/{sessionId}
    │   └── PATCH   /productivity/sessions/{sessionId}
    │
    ├── Application Tracking
    │   ├── GET     /productivity/sessions/{sessionId}/apps
    │   └── POST    /productivity/sessions/{sessionId}/apps
    │
    ├── Website Tracking
    │   ├── GET     /productivity/sessions/{sessionId}/websites
    │   └── POST    /productivity/sessions/{sessionId}/websites
    │
    └── Screenshot Tracking
        ├── GET     /productivity/sessions/{sessionId}/screenshots
        └── POST    /productivity/sessions/{sessionId}/screenshots
```