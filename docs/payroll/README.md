# Payroll Management

## Use Case
![Payroll Use Case](../../images/usecase/payroll-usecase.png)

## Sitemap
![Payroll Sitemap](../../images/sitemap/payroll_sitemap.png)

## System ERD
![Payroll ERD](../../images/erd/Payroll-erd.png)

## UI/UX Designs and Entity Relationship Diagrams

### Payroll Management

![Payroll Management](<../../images/uiux/payroll/Payroll%20Management%20(1).png>)

```mermaid
erDiagram
    EMPLOYEE ||--o{ PAYROLL : "has"

    EMPLOYEE {
        string id PK
        string name
        string department
    }

    PAYROLL {
        int id PK
        string employee_id FK
        date pay_period
        decimal base_salary
        decimal gross_pay
        decimal total_deductions
        decimal net_pay
        string status
    }
```

### Add Payroll

![Add Payroll](../../images/uiux/payroll/Add%20Payroll.png)

```mermaid
erDiagram
    EMPLOYEE ||--o{ PAYROLL : "has"
    PAYROLL ||--o{ PAYROLL_ITEM : "contains"
    COMPENSATION ||--o{ PAYROLL_ITEM : "references"

    EMPLOYEE {
        string id PK "e.g. EMP-00246"
        string name
        string role
        string department
        string employment_type
    }

    COMPENSATION {
        int id PK
        string type "ALLOWANCE, BENEFIT, BONUS, DEDUCTION"
        string name "e.g. Transport Allowance"
        string description
        decimal default_amount
        string status
    }

    PAYROLL {
        int id PK
        string employee_id FK
        date pay_period
        decimal base_salary
        decimal gross_pay
        decimal total_deductions
        decimal net_pay
        string status
    }

    PAYROLL_ITEM {
        int id PK
        int payroll_id FK
        int COMPENSATION_id FK
        decimal applied_amount
    }
```

### Payslips

![Payslips](../../images/uiux/payroll/Payslips.png)

```mermaid
erDiagram
    EMPLOYEE ||--o{ PAYROLL : "has"
    PAYROLL ||--o| PAYSLIP : "generates"

    PAYROLL {
        int id PK
        string employee_id FK
        date pay_period
        decimal gross_pay
        decimal total_deductions
        decimal net_pay
        string status
    }

    PAYSLIP {
        int id PK
        int payroll_id FK
        string payslip_number
        date issue_date
        string file_url
        string status
        datetime generated_at
    }
```

### Individual Payslip

![Payslip](../../images/uiux/payroll/payslip.png)

```mermaid
erDiagram
    PAYSLIP ||--|| PAYROLL : "represents"
    PAYROLL ||--o{ PAYROLL_ITEM : "contains"
    COMPENSATION ||--o{ PAYROLL_ITEM : "describes"

    PAYSLIP {
        int id PK
        int payroll_id FK
        string payslip_number
        date issue_date
        string file_url
        string status
    }

    PAYROLL {
        int id PK
        decimal base_salary
        decimal gross_pay
        decimal total_deductions
        decimal net_pay
    }

    PAYROLL_ITEM {
        int id PK
        int payroll_id FK
        int COMPENSATION_id FK
        decimal applied_amount
    }

    COMPENSATION {
        int id PK
        string type
        string name
    }
```

_Note: PAYSLIP_DETAIL is not required. Individual payslip lines are derived from PAYROLL and PAYROLL_ITEM, while PAYSLIP represents the generated payslip document._

### Compensation

![Benefit](../../images/uiux/payroll/Compensation.png)

```mermaid
erDiagram
    EMPLOYEE ||--o{ EMPLOYEE_COMPENSATION : "receives"
    COMPENSATION ||--o{ EMPLOYEE_COMPENSATION : "assigned_to"

    EMPLOYEE {
        string id PK
        string name
    }

    COMPENSATION {
        int id PK
        string name
        string description
        decimal default_amount
        string frequency
        string status
    }

    EMPLOYEE_COMPENSATION {
        int id PK
        string employee_id FK
        int compensation_id FK
        decimal applied_amount
        date effective_from
        date effective_to
        string status
    }
```

### Send Email

![Send Email](../../images/uiux/payroll/Send%20Email.png)

```mermaid
erDiagram
    EMPLOYEE ||--o{ EMAIL_LOG : "receives"
    PAYSLIP ||--o{ EMAIL_LOG : "attached_to"

    EMPLOYEE {
        string id PK
        string name
        string email
    }

    PAYSLIP {
        int id PK
        int payroll_id FK
        string file_url
    }

    EMAIL_LOG {
        int id PK
        string employee_id FK
        int payslip_id FK
        string recipient_email
        string subject
        string body
        datetime sent_at
        string status
    }
```

### Full Payroll ERD

```mermaid
erDiagram
    EMPLOYEE ||--o{ PAYROLL : "has"

    PAYROLL ||--o{ PAYROLL_ITEM : "contains"
    COMPENSATION ||--o{ PAYROLL_ITEM : "references"

    PAYROLL ||--o| PAYSLIP : "generates"

    EMPLOYEE ||--o{ EMPLOYEE_COMPENSATION : "receives"
    COMPENSATION ||--o{ EMPLOYEE_COMPENSATION : "assigned_to"

    EMPLOYEE ||--o{ EMAIL_LOG : "receives"
    PAYSLIP ||--o{ EMAIL_LOG : "attached_to"

    EMPLOYEE {
        string id PK
        string name
        string email
        string role
        string department
        string employment_type
    }

    PAYROLL {
        int id PK
        string employee_id FK
        date pay_period
        decimal base_salary
        decimal gross_pay
        decimal total_deductions
        decimal net_pay
        string currency
        string status
        datetime created_at
    }

    COMPENSATION {
        int id PK
        string type "ALLOWANCE, BENEFIT"
        string name
        string description
        decimal default_amount
        string frequency
        string status
    }

    PAYROLL_ITEM {
        int id PK
        int payroll_id FK
        int compensation_id FK
        decimal applied_amount
    }

    EMPLOYEE_COMPENSATION {
        int id PK
        string employee_id FK
        int compensation_id FK
        decimal applied_amount
        date effective_from
        date effective_to
        string status
    }

    PAYSLIP {
        int id PK
        int payroll_id FK
        string payslip_number
        date issue_date
        string file_url
        string status
        datetime generated_at
    }

    EMAIL_LOG {
        int id PK
        string employee_id FK
        int payslip_id FK
        string recipient_email
        string subject
        string body
        datetime sent_at
        string status
    }
```

## API Documentation

```text
BBV HR - Payroll Management API
│
├── Payrolls
│   ├── GET     /payrolls
│   ├── POST    /payrolls
│   ├── POST    /payrolls/import
│   ├── GET     /payrolls/{payrollId}
│   ├── PUT     /payrolls/{payrollId}
│   └── DELETE  /payrolls/{payrollId}
│
├── Payroll Items
│   ├── GET     /payrolls/{payrollId}/items
│   ├── POST    /payrolls/{payrollId}/items
│   ├── PUT     /payrolls/{payrollId}/items/{itemId}
│   └── DELETE  /payrolls/{payrollId}/items/{itemId}
│
├── Compensations
│   ├── GET     /compensations
│   ├── POST    /compensations
│   ├── GET     /compensations/{compensationId}
│   ├── PUT     /compensations/{compensationId}
│   └── DELETE  /compensations/{compensationId}
│
├── Employee Compensations
│   ├── GET     /employee-compensations
│   ├── POST    /employee-compensations
│   ├── GET     /employee-compensations/{employeeCompensationId}
│   ├── PUT     /employee-compensations/{employeeCompensationId}
│   └── DELETE  /employee-compensations/{employeeCompensationId}
│
├── Payslips
│   ├── GET     /payslips
│   ├── POST    /payrolls/{payrollId}/payslip
│   └── GET     /payslips/{payslipId}
│
└── Payslip Email
    ├── POST    /payslips/{payslipId}/send-email
    └── GET     /email-logs
```

![Payroll API](../../images/api-swagger/payroll/payroll.png)
![Compensation API](../../images/api-swagger/payroll/compensation.png)
![Payslip API](../../images/api-swagger/payroll/payslip.png)
