-- =============================================================================
-- COPILOT HR SYSTEM - POSTGRESQL DDL SCHEMA SCRIPT
-- Database: hr_db
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. ORGANIZATION MODULE TABLES
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS company_branch (
    branch_id VARCHAR(50) PRIMARY KEY,
    branch_name VARCHAR(150) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    is_headquarters BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS department (
    department_id VARCHAR(50) PRIMARY KEY,
    department_name VARCHAR(150) NOT NULL,
    branch_id VARCHAR(50) REFERENCES company_branch(branch_id) ON DELETE SET NULL,
    parent_department_id VARCHAR(50) REFERENCES department(department_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS position (
    position_id VARCHAR(50) PRIMARY KEY,
    position_title VARCHAR(150) NOT NULL,
    department_id VARCHAR(50) REFERENCES department(department_id) ON DELETE CASCADE,
    level VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS team (
    team_id VARCHAR(50) PRIMARY KEY,
    team_name VARCHAR(150) NOT NULL,
    department_id VARCHAR(50) REFERENCES department(department_id) ON DELETE CASCADE
);

-- -----------------------------------------------------------------------------
-- 2. EMPLOYEE DIRECTORY MODULE TABLES
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS employee (
    employee_id VARCHAR(50) PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    department_id VARCHAR(50) REFERENCES department(department_id) ON DELETE SET NULL,
    position_id VARCHAR(50) REFERENCES position(position_id) ON DELETE SET NULL,
    employment_status VARCHAR(50) DEFAULT 'Active',
    join_date DATE
);

CREATE TABLE IF NOT EXISTS team_member (
    member_id VARCHAR(50) PRIMARY KEY,
    team_id VARCHAR(50) REFERENCES team(team_id) ON DELETE CASCADE,
    employee_id VARCHAR(50) REFERENCES employee(employee_id) ON DELETE CASCADE,
    role_in_team VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS reporting_line (
    line_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) REFERENCES employee(employee_id) ON DELETE CASCADE,
    manager_id VARCHAR(50) REFERENCES employee(employee_id) ON DELETE CASCADE,
    reporting_type VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS employee_profile (
    profile_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) UNIQUE NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    avatar_url VARCHAR(255),
    gender VARCHAR(20),
    date_of_birth DATE
);

CREATE TABLE IF NOT EXISTS contract (
    contract_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    contract_number VARCHAR(100) UNIQUE NOT NULL,
    contract_type VARCHAR(50),
    start_date DATE,
    end_date DATE,
    base_salary NUMERIC(15, 2),
    status VARCHAR(50) DEFAULT 'Active'
);

CREATE TABLE IF NOT EXISTS education (
    education_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    degree VARCHAR(100),
    institution VARCHAR(150),
    field_of_study VARCHAR(100),
    start_year INT,
    end_year INT
);

CREATE TABLE IF NOT EXISTS certification (
    certification_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    name VARCHAR(150),
    issuing_organization VARCHAR(150),
    issue_date DATE,
    expiry_date DATE,
    credential_id VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS asset (
    asset_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    asset_name VARCHAR(150),
    serial_number VARCHAR(100) UNIQUE NOT NULL,
    asset_type VARCHAR(50),
    issue_date DATE,
    status VARCHAR(50) DEFAULT 'Active'
);

CREATE TABLE IF NOT EXISTS employee_document (
    document_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    document_name VARCHAR(150),
    document_type VARCHAR(50),
    file_url VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS leave_type (
    leave_type_id VARCHAR(50) PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    default_quota NUMERIC(5, 2)
);

CREATE TABLE IF NOT EXISTS employee_leave_balance (
    balance_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    leave_type_id VARCHAR(50) NOT NULL REFERENCES leave_type(leave_type_id) ON DELETE CASCADE,
    year INT,
    allocated_days NUMERIC(5, 2) DEFAULT 0.00,
    carried_over_days NUMERIC(5, 2) DEFAULT 0.00,
    used_days NUMERIC(5, 2) DEFAULT 0.00,
    pending_days NUMERIC(5, 2) DEFAULT 0.00
);

-- -----------------------------------------------------------------------------
-- 3. REQUEST MANAGEMENT MODULE TABLES
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS request_type (
    request_type_id VARCHAR(50) PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS workflow_step (
    step_id VARCHAR(50) PRIMARY KEY,
    request_type_id VARCHAR(50) NOT NULL REFERENCES request_type(request_type_id) ON DELETE CASCADE,
    step_order INT NOT NULL,
    approver_role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ticket_request (
    request_id VARCHAR(50) PRIMARY KEY,
    request_type_id VARCHAR(50) NOT NULL REFERENCES request_type(request_type_id) ON DELETE CASCADE,
    requester_id VARCHAR(50) NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    status VARCHAR(50) DEFAULT 'Pending',
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS approval_log (
    log_id VARCHAR(50) PRIMARY KEY,
    request_id VARCHAR(50) NOT NULL REFERENCES ticket_request(request_id) ON DELETE CASCADE,
    approver_id VARCHAR(50) REFERENCES employee(employee_id) ON DELETE SET NULL,
    action VARCHAR(50),
    comment TEXT,
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS request_attachment (
    attachment_id VARCHAR(50) PRIMARY KEY,
    request_id VARCHAR(50) NOT NULL REFERENCES ticket_request(request_id) ON DELETE CASCADE,
    file_name VARCHAR(150),
    file_url VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS handover_task (
    task_id VARCHAR(50) PRIMARY KEY,
    request_id VARCHAR(50) NOT NULL REFERENCES ticket_request(request_id) ON DELETE CASCADE,
    assignee_id VARCHAR(50) REFERENCES employee(employee_id) ON DELETE SET NULL,
    task_description TEXT,
    status VARCHAR(50) DEFAULT 'Pending'
);
