from django.db import models
from .base import BaseModel

class Requirement(BaseModel):
    department_id = models.UUIDField()
    position_id = models.UUIDField()
    hiring_manager_id = models.UUIDField()
    priority = models.CharField(max_length=50)
    employment_type = models.CharField(max_length=50)
    status = models.CharField(max_length=50, default='DRAFT')
    description = models.TextField(blank=True, null=True)

    class Meta:
        db_table = 'recruitment_requirements'
