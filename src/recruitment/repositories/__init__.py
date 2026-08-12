from .base import BaseRepository
from recruitment.models import Requirement, Job, Application, Schedule, Interview, Offer, RecruitmentTemplate

class RequirementRepository(BaseRepository):
    def __init__(self):
        super().__init__(Requirement)

class JobRepository(BaseRepository):
    def __init__(self):
        super().__init__(Job)

class ApplicationRepository(BaseRepository):
    def __init__(self):
        super().__init__(Application)

class ScheduleRepository(BaseRepository):
    def __init__(self):
        super().__init__(Schedule)

class InterviewRepository(BaseRepository):
    def __init__(self):
        super().__init__(Interview)

class OfferRepository(BaseRepository):
    def __init__(self):
        super().__init__(Offer)

class RecruitmentTemplateRepository(BaseRepository):
    def __init__(self):
        super().__init__(RecruitmentTemplate)
