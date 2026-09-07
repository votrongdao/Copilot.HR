-- =============================================================================
-- COPILOT HR SYSTEM - MOCK DATA SEED SCRIPT (DML)
-- Database: hr_db
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. SEED ORGANIZATION DATA
-- -----------------------------------------------------------------------------
INSERT INTO company_branch (branch_id, branch_name, address, phone, email, is_headquarters)
VALUES 
('BR-01', 'Ho Chi Minh Head Office', '72 Le Thanh Ton, District 1, HCMC', '+84 28 3822 1234', 'hcm.office@copilothr.com', TRUE),
('BR-02', 'Hanoi Regional Branch', '54 Lieu Giai, Ba Dinh, Hanoi', '+84 24 3766 5678', 'hanoi.office@copilothr.com', FALSE)
ON CONFLICT (branch_id) DO NOTHING;

INSERT INTO department (department_id, department_name, branch_id, parent_department_id)
VALUES 
('DEP-TECH', 'Technology & Engineering', 'BR-01', NULL),
('DEP-HR', 'Human Resources', 'BR-01', NULL),
('DEP-FIN', 'Finance & Accounting', 'BR-01', NULL),
('DEP-SW', 'Software Development', 'BR-01', 'DEP-TECH')
ON CONFLICT (department_id) DO NOTHING;

INSERT INTO position (position_id, position_title, department_id, level)
VALUES 
('POS-DEV-SR', 'Senior Software Engineer', 'DEP-SW', 'Senior'),
('POS-DEV-JR', 'Junior Software Engineer', 'DEP-SW', 'Junior'),
('POS-HR-MGR', 'HR Manager', 'DEP-HR', 'Management'),
('POS-FIN-ACC', 'Senior Accountant', 'DEP-FIN', 'Senior')
ON CONFLICT (position_id) DO NOTHING;

INSERT INTO team (team_id, team_name, department_id)
VALUES 
('TEAM-BE', 'Backend Engineering Team', 'DEP-SW'),
('TEAM-FE', 'Frontend UI/UX Team', 'DEP-SW'),
('TEAM-TALENT', 'Talent Acquisition Team', 'DEP-HR')
ON CONFLICT (team_id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 2. SEED EMPLOYEE DIRECTORY DATA
-- -----------------------------------------------------------------------------
INSERT INTO employee (employee_id, email, department_id, position_id, employment_status, join_date)
VALUES 
('EMP-0024', 'khang.nguyen@copilothr.com', 'DEP-SW', 'POS-DEV-SR', 'Active', '2022-03-15'),
('EMP-0025', 'linh.tran@copilothr.com', 'DEP-HR', 'POS-HR-MGR', 'Active', '2021-06-01'),
('EMP-0026', 'minh.pham@copilothr.com', 'DEP-SW', 'POS-DEV-JR', 'Probation', '2023-11-10'),
('EMP-0027', 'lan.vu@copilothr.com', 'DEP-FIN', 'POS-FIN-ACC', 'Active', '2020-01-20')
ON CONFLICT (employee_id) DO NOTHING;

INSERT INTO team_member (member_id, team_id, employee_id, role_in_team)
VALUES 
('TM-01', 'TEAM-BE', 'EMP-0024', 'Tech Lead'),
('TM-02', 'TEAM-BE', 'EMP-0026', 'Developer'),
('TM-03', 'TEAM-TALENT', 'EMP-0025', 'Team Lead')
ON CONFLICT (member_id) DO NOTHING;

INSERT INTO reporting_line (line_id, employee_id, manager_id, reporting_type)
VALUES 
('RL-01', 'EMP-0024', 'EMP-0025', 'Direct'),
('RL-02', 'EMP-0026', 'EMP-0024', 'Direct')
ON CONFLICT (line_id) DO NOTHING;

INSERT INTO employee_profile (profile_id, employee_id, first_name, last_name, phone, avatar_url, gender, date_of_birth)
VALUES 
('PROF-0024', 'EMP-0024', 'Khang', 'Nguyen', '+84 909 123 456', 'https://cdn.copilothr.com/avatars/emp-0024.jpg', 'Male', '1995-08-20'),
('PROF-0025', 'EMP-0025', 'Linh', 'Tran', '+84 908 234 567', 'https://cdn.copilothr.com/avatars/emp-0025.jpg', 'Female', '1992-04-12'),
('PROF-0026', 'EMP-0026', 'Minh', 'Pham', '+84 907 345 678', 'https://cdn.copilothr.com/avatars/emp-0026.jpg', 'Male', '1998-12-05'),
('PROF-0027', 'EMP-0027', 'Lan', 'Vu', '+84 906 456 789', 'https://cdn.copilothr.com/avatars/emp-0027.jpg', 'Female', '1991-09-18')
ON CONFLICT (profile_id) DO NOTHING;

INSERT INTO contract (contract_id, employee_id, contract_number, contract_type, start_date, end_date, base_salary, status)
VALUES 
('CNT-001', 'EMP-0024', 'HDLD-2022-0024', 'Indefinite', '2022-03-15', NULL, 35000000.00, 'Active'),
('CNT-002', 'EMP-0025', 'HDLD-2021-0025', 'Indefinite', '2021-06-01', NULL, 40000000.00, 'Active'),
('CNT-003', 'EMP-0026', 'HDLD-2023-0026', 'Probation', '2023-11-10', '2024-01-10', 15000000.00, 'Active')
ON CONFLICT (contract_id) DO NOTHING;

INSERT INTO education (education_id, employee_id, degree, institution, field_of_study, start_year, end_year)
VALUES 
('EDU-01', 'EMP-0024', 'Bachelor of Computer Science', 'VNU University of Technology', 'Software Engineering', 2013, 2017),
('EDU-02', 'EMP-0025', 'Bachelor of Business Administration', 'UEH University', 'Human Resource Management', 2010, 2014)
ON CONFLICT (education_id) DO NOTHING;

INSERT INTO certification (certification_id, employee_id, name, issuing_organization, issue_date, expiry_date, credential_id)
VALUES 
('CERT-01', 'EMP-0024', 'AWS Certified Solutions Architect', 'Amazon Web Services', '2023-01-15', '2026-01-15', 'AWS-ASA-9988'),
('CERT-02', 'EMP-0024', 'Oracle Certified Professional Java SE 17', 'Oracle', '2022-05-20', NULL, 'OCP-JAVA-17')
ON CONFLICT (certification_id) DO NOTHING;

INSERT INTO asset (asset_id, employee_id, asset_name, serial_number, asset_type, issue_date, status)
VALUES 
('AST-01', 'EMP-0024', 'MacBook Pro 16" M2 Max', 'C02XX9988M2', 'Laptop', '2022-03-15', 'Assigned'),
('AST-02', 'EMP-0024', 'Dell UltraSharp 27" 4K Monitor', 'CN-0M9988-1234', 'Monitor', '2022-03-15', 'Assigned'),
('AST-03', 'EMP-0025', 'ThinkPad X1 Carbon Gen 10', 'PF-334455XX', 'Laptop', '2021-06-01', 'Assigned')
ON CONFLICT (asset_id) DO NOTHING;

INSERT INTO employee_document (document_id, employee_id, document_name, document_type, file_url, uploaded_at)
VALUES 
('DOC-01', 'EMP-0024', 'National_ID_Card.pdf', 'Identity', 'https://cdn.copilothr.com/docs/emp-0024-id.pdf', CURRENT_TIMESTAMP),
('DOC-02', 'EMP-0024', 'Degree_Certificate.pdf', 'Education', 'https://cdn.copilothr.com/docs/emp-0024-degree.pdf', CURRENT_TIMESTAMP)
ON CONFLICT (document_id) DO NOTHING;

INSERT INTO leave_type (leave_type_id, code, name, description, default_quota)
VALUES 
('LT-01', 'ANNUAL', 'Annual Paid Leave', 'Standard statutory annual vacation allowance', 12.00),
('LT-02', 'SICK', 'Sick Leave', 'Medical & health recuperation leave', 30.00),
('LT-03', 'MATERNITY', 'Maternity Leave', 'Childbirth and maternity protection leave', 180.00)
ON CONFLICT (leave_type_id) DO NOTHING;

INSERT INTO employee_leave_balance (balance_id, employee_id, leave_type_id, year, allocated_days, carried_over_days, used_days, pending_days)
VALUES 
('BAL-01', 'EMP-0024', 'LT-01', 2026, 12.00, 3.00, 2.00, 1.00),
('BAL-02', 'EMP-0024', 'LT-02', 2026, 30.00, 0.00, 0.00, 0.00),
('BAL-03', 'EMP-0025', 'LT-01', 2026, 15.00, 5.00, 4.00, 0.00)
ON CONFLICT (balance_id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 3. SEED REQUEST MANAGEMENT DATA
-- -----------------------------------------------------------------------------
INSERT INTO request_type (request_type_id, code, name, description)
VALUES 
('RT-LEAVE', 'REQ_LEAVE', 'Leave Request', 'Employee annual/sick leave application'),
('RT-EQUIP', 'REQ_EQUIPMENT', 'Hardware Request', 'Request new laptop or workstation gear'),
('RT-OT', 'REQ_OVERTIME', 'Overtime Claim', 'Claim overtime compensation hours')
ON CONFLICT (request_type_id) DO NOTHING;

INSERT INTO workflow_step (step_id, request_type_id, step_order, approver_role)
VALUES 
('WS-01', 'RT-LEAVE', 1, 'Direct Manager'),
('WS-02', 'RT-LEAVE', 2, 'HR Admin')
ON CONFLICT (step_id) DO NOTHING;

INSERT INTO ticket_request (request_id, request_type_id, requester_id, status, reason, created_at)
VALUES 
('REQ-2026-001', 'RT-LEAVE', 'EMP-0024', 'Pending', 'Personal annual vacation trip', CURRENT_TIMESTAMP)
ON CONFLICT (request_id) DO NOTHING;

INSERT INTO approval_log (log_id, request_id, approver_id, action, comment, action_date)
VALUES 
('LOG-01', 'REQ-2026-001', 'EMP-0025', 'APPROVED', 'Approved annual leave request', CURRENT_TIMESTAMP)
ON CONFLICT (log_id) DO NOTHING;

INSERT INTO request_attachment (attachment_id, request_id, file_name, file_url, uploaded_at)
VALUES 
('ATT-01', 'REQ-2026-001', 'Flight_Ticket.pdf', 'https://cdn.copilothr.com/requests/att-01.pdf', CURRENT_TIMESTAMP)
ON CONFLICT (attachment_id) DO NOTHING;

INSERT INTO handover_task (task_id, request_id, assignee_id, task_description, status)
VALUES 
('HT-01', 'REQ-2026-001', 'EMP-0026', 'Cover backend deployment during vacation', 'Pending')
ON CONFLICT (task_id) DO NOTHING;
