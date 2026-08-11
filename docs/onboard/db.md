//////////////////////////////////////////////////////
// APPLICATION MANAGEMENT
//////////////////////////////////////////////////////

Table CANDIDATE {
  candidate_id bigint [pk]
  full_name varchar
  email varchar
  phone varchar
  address varchar
  cv_url varchar
  source varchar

  created_at datetime
  updated_at datetime
}

Table POSITION {
  position_id bigint [pk]
  position_name varchar
  department_name varchar
  employment_type varchar
  status varchar
}

Table APPLICATION_STAGE {
  application_stage_id bigint [pk]
  stage_name varchar
  stage_order int
  description varchar
}

Table USER {
  user_id bigint [pk]
  team_id bigint

  full_name varchar
  email varchar
  status varchar

  created_at datetime
  updated_at datetime
}

Table APPLICATION {
  application_id bigint [pk]

  candidate_id bigint
  position_id bigint
  application_stage_id bigint
  owner_user_id bigint

  status varchar

  applied_at datetime
  moved_to_stage_at datetime

  rejected_at datetime
  rejection_reason varchar

  created_at datetime
  updated_at datetime
}

Table INTERVIEW {
  interview_id bigint [pk]

  application_id bigint
  interviewer_user_id bigint

  interview_round varchar
  interview_type varchar

  scheduled_at datetime
  duration_minutes int

  location varchar
  meeting_link varchar

  status varchar
  notes text

  created_at datetime
  updated_at datetime
}

Table APPLICATION_EVALUATION {
  evaluation_id bigint [pk]

  application_id bigint
  evaluator_user_id bigint

  technical_score int
  experience_score int
  communication_score int
  culture_fit_score int

  overall_score decimal
  recommendation varchar
  comment text

  created_at datetime
}


//////////////////////////////////////////////////////
// OFFER MANAGEMENT
//////////////////////////////////////////////////////

Table OFFER_TEMPLATE {
  offer_template_id bigint [pk]

  template_name varchar
  status varchar
  content text

  created_at datetime
  updated_at datetime
}

Table EMAIL_TEMPLATE {
  email_template_id bigint [pk]

  template_name varchar
  subject varchar
  content text

  status varchar

  created_at datetime
  updated_at datetime
}

Table CANDIDATE_FORM_TEMPLATE {
  candidate_form_template_id bigint [pk]

  template_name varchar
  status varchar

  created_at datetime
  updated_at datetime
}

Table OFFER {
  offer_id bigint [pk]

  application_id bigint

  offer_template_id bigint
  email_template_id bigint
  candidate_form_template_id bigint
  owner_user_id bigint

  offer_code varchar
  version_number int
  status varchar

  salary_amount decimal
  salary_currency varchar

  response_deadline datetime

  proposed_start_date date
  final_start_date date

  sent_at datetime

  created_at datetime
  updated_at datetime
}

Table OFFER_RESPONSE {
  offer_response_id bigint [pk]

  offer_id bigint

  response_type varchar
  message text

  responded_at datetime
}


//////////////////////////////////////////////////////
// ONBOARDING INTAKE REVIEW
//////////////////////////////////////////////////////

Table ONBOARDING_SUBMISSION {
  submission_id bigint [pk]

  offer_id bigint [unique]

  status varchar

  submitted_at datetime
  processed_at datetime

  created_at datetime
  updated_at datetime
}

Table SUBMISSION_DOCUMENT {
  document_id bigint [pk]

  submission_id bigint

  document_type varchar
  file_name varchar
  file_url varchar

  upload_status varchar
  extraction_status varchar
  verification_status varchar

  uploaded_at datetime
}

Table FIELD_MAPPING {
  field_mapping_id bigint [pk]

  submission_id bigint

  source_field varchar
  source_value text

  target_field varchar
  mapped_value text

  confidence_score decimal
  mapping_status varchar

  corrected_value text
}

Table GENERATED_OUTPUT {
  generated_output_id bigint [pk]

  submission_id bigint

  output_type varchar
  output_name varchar

  status varchar

  generated_at datetime
}

Table INTAKE_REVIEW {
  intake_review_id bigint [pk]

  submission_id bigint
  reviewer_user_id bigint

  status varchar
  review_notes text

  reviewed_at datetime
  approved_at datetime

  created_at datetime
  updated_at datetime
}


//////////////////////////////////////////////////////
// ONBOARDING BOARD
//////////////////////////////////////////////////////

Table EMPLOYEE {
  employee_id bigint [pk]

  candidate_id bigint
  manager_user_id bigint

  employee_code varchar
  work_email varchar
  work_location varchar

  employment_status varchar
  joined_at date

  created_at datetime
  updated_at datetime
}

Table ONBOARDING_STAGE {
  onboarding_stage_id bigint [pk]

  stage_name varchar
  stage_order int
  description varchar
}

Table ONBOARDING_CASE {
  onboarding_case_id bigint [pk]

  offer_id bigint [unique]
  employee_id bigint
  onboarding_stage_id bigint

  priority varchar
  status varchar

  planned_start_date date
  actual_start_date date

  created_at datetime
  completed_at datetime
  updated_at datetime
}

Table ONBOARDING_TASK {
  onboarding_task_id bigint [pk]

  onboarding_case_id bigint
  task_template_id bigint

  task_name varchar
  description text

  status varchar
  priority varchar

  due_at datetime
  started_at datetime
  completed_at datetime

  created_at datetime
  updated_at datetime
}

Table READINESS_CHECKLIST_ITEM {
  readiness_item_id bigint [pk]

  onboarding_case_id bigint

  item_name varchar
  description text

  status varchar
  is_required boolean

  completed_at datetime
}

Table ONBOARDING_BLOCKER {
  blocker_id bigint [pk]

  onboarding_case_id bigint
  onboarding_task_id bigint

  blocker_type varchar
  description text

  status varchar
  severity varchar

  reported_at datetime
  resolved_at datetime
}


//////////////////////////////////////////////////////
// MY ASSIGNED TASKS
//////////////////////////////////////////////////////

Table TASK_TEMPLATE {
  task_template_id bigint [pk]

  task_name varchar
  description text

  default_priority varchar
  default_due_days int

  status varchar

  created_at datetime
  updated_at datetime
}

Table TEAM {
  team_id bigint [pk]

  team_name varchar
  description varchar

  status varchar

  created_at datetime
  updated_at datetime
}

Table ROLE {
  role_id bigint [pk]

  role_name varchar
  description varchar

  created_at datetime
  updated_at datetime
}

Table USER_ROLE {
  user_id bigint
  role_id bigint

  assigned_at datetime

  indexes {
    (user_id, role_id) [pk]
  }
}

Table TASK_ASSIGNMENT {
  task_assignment_id bigint [pk]

  onboarding_task_id bigint

  assigned_user_id bigint
  assigned_by_user_id bigint

  assignment_status varchar

  assigned_at datetime
  completed_at datetime
}

Table TASK_COMMENT {
  task_comment_id bigint [pk]

  onboarding_task_id bigint
  author_user_id bigint

  comment text

  created_at datetime
}


//////////////////////////////////////////////////////
// TRACKING ONBOARDING PROGRESS
//////////////////////////////////////////////////////

Table PROBATION {
  probation_id bigint [pk]

  onboarding_case_id bigint [unique]

  start_date date
  end_date date

  status varchar

  extension_count int

  completed_at datetime

  created_at datetime
  updated_at datetime
}

Table SELF_REVIEW {
  self_review_id bigint [pk]

  probation_id bigint

  status varchar

  due_at datetime
  requested_at datetime
  submitted_at datetime

  review_content text

  created_at datetime
  updated_at datetime
}

Table EVALUATION {
  evaluation_id bigint [pk]

  probation_id bigint

  evaluation_round int

  scheduled_at datetime
  meeting_location varchar

  status varchar
  result varchar

  summary text

  completed_at datetime

  created_at datetime
  updated_at datetime
}

Table EVALUATION_REVIEWER {
  evaluation_id bigint
  reviewer_user_id bigint

  recommendation varchar
  feedback text
  score decimal

  submitted_at datetime

  indexes {
    (evaluation_id, reviewer_user_id) [pk]
  }
}


//////////////////////////////////////////////////////
// APPLICATION RELATIONSHIPS
//////////////////////////////////////////////////////

Ref: CANDIDATE.candidate_id < APPLICATION.candidate_id

Ref: POSITION.position_id < APPLICATION.position_id

Ref: APPLICATION_STAGE.application_stage_id < APPLICATION.application_stage_id

Ref: USER.user_id < APPLICATION.owner_user_id

Ref: APPLICATION.application_id < INTERVIEW.application_id

Ref: USER.user_id < INTERVIEW.interviewer_user_id

Ref: APPLICATION.application_id < APPLICATION_EVALUATION.application_id

Ref: USER.user_id < APPLICATION_EVALUATION.evaluator_user_id


//////////////////////////////////////////////////////
// OFFER RELATIONSHIPS
//////////////////////////////////////////////////////

Ref: APPLICATION.application_id < OFFER.application_id

Ref: OFFER_TEMPLATE.offer_template_id < OFFER.offer_template_id

Ref: EMAIL_TEMPLATE.email_template_id < OFFER.email_template_id

Ref: CANDIDATE_FORM_TEMPLATE.candidate_form_template_id < OFFER.candidate_form_template_id

Ref: USER.user_id < OFFER.owner_user_id

Ref: OFFER.offer_id < OFFER_RESPONSE.offer_id


//////////////////////////////////////////////////////
// INTAKE REVIEW RELATIONSHIPS
//////////////////////////////////////////////////////

Ref: OFFER.offer_id - ONBOARDING_SUBMISSION.offer_id

Ref: ONBOARDING_SUBMISSION.submission_id < SUBMISSION_DOCUMENT.submission_id

Ref: ONBOARDING_SUBMISSION.submission_id < FIELD_MAPPING.submission_id

Ref: ONBOARDING_SUBMISSION.submission_id < GENERATED_OUTPUT.submission_id

Ref: ONBOARDING_SUBMISSION.submission_id < INTAKE_REVIEW.submission_id

Ref: USER.user_id < INTAKE_REVIEW.reviewer_user_id


//////////////////////////////////////////////////////
// ONBOARDING BOARD RELATIONSHIPS
//////////////////////////////////////////////////////

Ref: CANDIDATE.candidate_id - EMPLOYEE.candidate_id

Ref: USER.user_id < EMPLOYEE.manager_user_id

Ref: EMPLOYEE.employee_id < ONBOARDING_CASE.employee_id

Ref: OFFER.offer_id - ONBOARDING_CASE.offer_id

Ref: ONBOARDING_STAGE.onboarding_stage_id < ONBOARDING_CASE.onboarding_stage_id

Ref: ONBOARDING_CASE.onboarding_case_id < ONBOARDING_TASK.onboarding_case_id

Ref: ONBOARDING_CASE.onboarding_case_id < READINESS_CHECKLIST_ITEM.onboarding_case_id

Ref: ONBOARDING_CASE.onboarding_case_id < ONBOARDING_BLOCKER.onboarding_case_id

Ref: ONBOARDING_TASK.onboarding_task_id < ONBOARDING_BLOCKER.onboarding_task_id


//////////////////////////////////////////////////////
// MY ASSIGNED TASKS RELATIONSHIPS
//////////////////////////////////////////////////////

Ref: TASK_TEMPLATE.task_template_id < ONBOARDING_TASK.task_template_id

Ref: ONBOARDING_TASK.onboarding_task_id < TASK_ASSIGNMENT.onboarding_task_id

Ref: USER.user_id < TASK_ASSIGNMENT.assigned_user_id

Ref: USER.user_id < TASK_ASSIGNMENT.assigned_by_user_id

Ref: TEAM.team_id < USER.team_id

Ref: USER.user_id < USER_ROLE.user_id

Ref: ROLE.role_id < USER_ROLE.role_id

Ref: ONBOARDING_TASK.onboarding_task_id < TASK_COMMENT.onboarding_task_id

Ref: USER.user_id < TASK_COMMENT.author_user_id


//////////////////////////////////////////////////////
// TRACKING PROGRESS RELATIONSHIPS
//////////////////////////////////////////////////////

Ref: ONBOARDING_CASE.onboarding_case_id - PROBATION.onboarding_case_id

Ref: PROBATION.probation_id < SELF_REVIEW.probation_id

Ref: PROBATION.probation_id < EVALUATION.probation_id

Ref: EVALUATION.evaluation_id < EVALUATION_REVIEWER.evaluation_id

Ref: USER.user_id < EVALUATION_REVIEWER.reviewer_user_id