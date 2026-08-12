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
        if job:
            return self.repo.update(job, status='PUBLISHED')
        return None

__all__ = ['RequirementService', 'JobService']
