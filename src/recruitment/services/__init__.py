import random
from recruitment.repositories import (
    RequirementRepository, JobRepository, ApplicationRepository,
    ScheduleRepository, InterviewRepository, OfferRepository, RecruitmentTemplateRepository
)

class RequirementService:
    def __init__(self):
        self.repo = RequirementRepository()
        
    def get_all(self, **filters):
        return self.repo.get_all(**filters)
        
    def get_by_id(self, req_id):
        return self.repo.get_by_id(req_id)
        
    def create(self, data):
        return self.repo.create(**data)
        
    def update(self, req_id, data):
        req = self.repo.get_by_id(req_id)
        if req:
            return self.repo.update(req, **data)
        return None
        
    def delete(self, req_id):
        req = self.repo.get_by_id(req_id)
        if req:
            self.repo.delete(req)
            return True
        return False

class JobService:
    def __init__(self):
        self.repo = JobRepository()

    def get_all(self, **filters):
        return self.repo.get_all(**filters)

    def get_by_id(self, job_id):
        return self.repo.get_by_id(job_id)

    def create(self, data):
        return self.repo.create(**data)

    def update(self, job_id, data):
        job = self.repo.get_by_id(job_id)
        if job:
            return self.repo.update(job, **data)
        return None

    def publish_job(self, job_id, integration_ids=None):
        job = self.repo.get_by_id(job_id)
        if not job:
            return {"error": "Not Found"}

        if not job.get("title") or not job.get('requirement_id'):
            return {"error": "Missing title or requirement_id"}

        job['status'] = "Published"

        update_job = self.repo.update(job_id, **job)
        return {"success": True, "data": update_job}

    def delete(self, job_id):
        job = self.repo.get_by_id(job_id)
        if not job:
            return {"error": "Not Found"}
        if job.get("status") == "Published":
            return {"error": "LOCKED", "msg" :"Job is Published"}
        
        self.repo.delete(job)
        return {"success":True}

class ApplicationService:
    def __init__(self):
        self.repo = ApplicationRepository()
    
    def create(self, data):
        if not data.get("job_id") or not data.get("candidate_id"):
            return {"error": "MISSING_FIELDS", "msg": "Missing job_id or candidate_id"}

        data['status'] = "New"
        data['stage'] = "Applied"
        
        if 'ai_score' not in data:
            data['ai_score'] = round(random.uniform(50.0, 99.0), 1)

        new_cv = self.repo.create(**data)
        return {"success": True, "data": new_cv}

__all__ = ['RequirementService', 'JobService', 'ApplicationService']
