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

## Unit Test Summary

| Test Group | Number of Tests |
| --- | --- |
| Payroll Management | 10 |
| Payroll Import | 6 |
| Compensation | 6 |
| Employee Compensation | 7 |
| Payroll Item | 5 |
| Payslip | 7 |
| Payslip Email | 5 |
| API / Controller | 4 |
| **Total** | **50** |

### 1. Payroll Management — 10 Tests

| Unit Test | Expected Result | Priority |
| --- | --- | --- |
| Create payroll with valid data | Payroll is created successfully | Critical |
| Create payroll for a nonexistent employee | Request is rejected | Critical |
| Create payroll with missing required fields | Validation error is returned | Critical |
| Create duplicate payroll for the same employee and pay period | Duplicate payroll is rejected | Critical |
| Update existing payroll | Payroll is updated successfully | Critical |
| Create payroll with invalid monetary values | Validation error is returned | High |
| Get payroll by valid ID | Correct payroll is returned | High |
| Get nonexistent payroll | Not Found is returned | High |
| Get payroll list with pagination | Correct paginated result is returned | High |
| Delete existing payroll | Payroll is deleted successfully | High |

### 2. Payroll Import — 6 Tests

| Unit Test | Expected Result | Priority |
| --- | --- | --- |
| Import valid Excel payroll file | Payroll records are imported successfully | Critical |
| Import multiple valid payroll rows | All valid rows are imported | Critical |
| Import row with nonexistent employee | Invalid row is rejected | Critical |
| Import duplicate employee and pay period | Duplicate record is detected | Critical |
| Import unsupported file format | File is rejected | High |
| Import payroll row with invalid data | Validation error is returned | High |

### 3. Compensation — 6 Tests

| Unit Test | Expected Result | Priority |
| --- | --- | --- |
| Create valid compensation | Compensation is created successfully | Critical |
| Create compensation with invalid type | Validation error is returned | Critical |
| Create compensation without required fields | Validation error is returned | High |
| Create compensation with invalid amount | Validation error is returned | High |
| Update compensation | Compensation is updated successfully | High |
| Delete or deactivate compensation | Compensation is deleted or deactivated | High |

### 4. Employee Compensation — 7 Tests

| Unit Test | Expected Result | Priority |
| --- | --- | --- |
| Assign compensation to valid employee | Assignment is created successfully | Critical |
| Assign compensation to nonexistent employee | Assignment is rejected | Critical |
| Assign nonexistent compensation | Assignment is rejected | Critical |
| Assign custom applied_amount | Correct custom amount is stored | Critical |
| Set effective_to before effective_from | Validation error is returned | Critical |
| Assign inactive compensation | Assignment is rejected | High |
| Update employee compensation | Assignment is updated successfully | High |

### 5. Payroll Item — 5 Tests

| Unit Test | Expected Result | Priority |
| --- | --- | --- |
| Add compensation to payroll | Payroll item is created successfully | Critical |
| Add item to nonexistent payroll | Not Found is returned | Critical |
| Add nonexistent compensation to payroll | Request is rejected | Critical |
| Update payroll item | Payroll item is updated successfully | High |
| Delete payroll item | Payroll item is removed successfully | High |

### 6. Payslip — 7 Tests

| Unit Test | Expected Result | Priority |
| --- | --- | --- |
| Generate payslip from valid payroll | Payslip is generated successfully | Critical |
| Generate payslip for nonexistent payroll | Not Found is returned | Critical |
| Generate second payslip for the same payroll | Duplicate payslip is rejected | Critical |
| Map payroll values to payslip | Payslip values match payroll data | Critical |
| Map payroll items and compensations to payslip | Correct compensation lines are generated | Critical |
| Generate unique payslip_number | Unique payslip number is generated | High |
| Generate payslip document | Valid file_url is stored | High |

### 7. Payslip Email — 5 Tests

| Unit Test | Expected Result | Priority |
| --- | --- | --- |
| Send valid payslip email | Email is sent successfully | Critical |
| Send email for nonexistent payslip | Not Found is returned | Critical |
| Send payslip when employee has no email | Email sending is rejected | Critical |
| Email provider succeeds | Successful EMAIL_LOG is recorded | Critical |
| Email provider fails | Failed EMAIL_LOG is recorded | Critical |

### 8. API / Controller — 4 Tests

| Endpoint | Scenario | Expected Result | Priority |
| --- | --- | --- | --- |
| GET /payrolls | Valid request with pagination | 200 OK with paginated result | Critical |
| POST /payrolls | Valid or invalid request | 201 Created or 400 Bad Request | Critical |
| POST /payrolls/{payrollId}/payslip | Valid payroll or duplicate payslip | 201 Created or 409 Conflict | Critical |
| POST /payslips/{payslipId}/send-email | Valid request or provider failure | Email result is handled correctly | Critical |

## Complex Unit Test Sequence Diagrams

### 1. Bulk Payroll Import Test Flow
This sequence diagram illustrates the complex unit testing scenario for importing payrolls from a bulk Excel/CSV file, highlighting parsing, validation, duplicate checking, and database mocking.

```mermaid
sequenceDiagram
    participant Test
    participant PayrollController
    participant ImportService
    participant Parser
    participant DB_Mock

    Test->>PayrollController: POST /payrolls/import (Excel File)
    PayrollController->>ImportService: ProcessFile(file)
    ImportService->>Parser: Parse(file)
    Parser-->>ImportService: List<PayrollRecord>
    
    loop For Each Record
        ImportService->>DB_Mock: CheckEmployeeExists(empId)
        DB_Mock-->>ImportService: Employee (or null)
        alt Employee Exists
            ImportService->>DB_Mock: CheckDuplicatePayroll(empId, payPeriod)
            DB_Mock-->>ImportService: Duplicate (True/False)
            alt Is Not Duplicate
                ImportService->>ImportService: Calculate gross_pay, deductions, net_pay
            else Duplicate
                ImportService->>ImportService: Track Error: "Duplicate Entry"
            end
        else Employee Not Found
            ImportService->>ImportService: Track Error: "Employee Not Found"
        end
    end
    
    ImportService->>DB_Mock: BulkInsert(validPayrolls)
    DB_Mock-->>ImportService: Success (Rows Affected)
    ImportService-->>PayrollController: ImportSummary (Success/Failed metrics)
    PayrollController-->>Test: HTTP 200 OK (with ImportSummary)
    
    Test->>Test: Assert HTTP Status == 200
    Test->>Test: Assert DB_Mock.BulkInsert called with N valid records
```

### 2. Payslip Generation & Email Test Flow
This sequence diagram outlines the testing flow for generating a payslip document and sending it to an employee via email, showcasing how external services (PDF Generator, Email Provider) are mocked in a unit test.

```mermaid
sequenceDiagram
    participant Test
    participant PayslipController
    participant PayslipService
    participant PDFGenerator_Mock
    participant EmailService_Mock
    participant DB_Mock

    Test->>PayslipController: POST /payslips/{id}/send-email
    PayslipController->>PayslipService: SendPayslipEmail(payslipId)
    
    PayslipService->>DB_Mock: GetPayslipAndEmployee(payslipId)
    DB_Mock-->>PayslipService: Payslip & Employee Data
    
    PayslipService->>PDFGenerator_Mock: GeneratePDF(payslipData)
    PDFGenerator_Mock-->>PayslipService: Generated Document URL / Buffer
    
    PayslipService->>EmailService_Mock: SendEmail(employee.email, Document)
    EmailService_Mock-->>PayslipService: Delivery Status (Sent)
    
    PayslipService->>DB_Mock: Insert(EMAIL_LOG, status: 'Sent')
    DB_Mock-->>PayslipService: Log Saved
    
    PayslipService-->>PayslipController: Success Message
    PayslipController-->>Test: HTTP 200 OK
    
    Test->>Test: Assert HTTP Status == 200
    Test->>Test: Assert EmailService_Mock.SendEmail was called exactly once
    Test->>Test: Assert DB_Mock.Insert(EMAIL_LOG) was called with correct data
```
