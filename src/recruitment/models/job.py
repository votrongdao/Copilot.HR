from django.db import models
from .base import BaseModel

class Job(BaseModel):
    title = models.CharField(max_length=255)
    department_id = models.UUIDField()
    position_id = models.UUIDField()
    hiring_manager_id = models.UUIDField()
    employment_type = models.CharField(max_length=50)
    work_mode = models.CharField(max_length=50)
    status = models.CharField(max_length=50, default='DRAFT')
    requirement = models.ForeignKey('Requirement', on_delete=models.SET_NULL, null=True, blank=True)

    class Meta:
        db_table = 'recruitment_jobs'
