from django.db import models
from .base import BaseModel
from .job import Job

class Application(BaseModel):
    candidate_id = models.UUIDField()
    job = models.ForeignKey(Job, on_delete=models.CASCADE, related_name='applications')
    stage_id = models.UUIDField(null=True, blank=True)
    source_id = models.UUIDField(null=True, blank=True)
    owner_user_id = models.UUIDField(null=True, blank=True)
    status = models.CharField(max_length=50, default='NEW')

    class Meta:
        db_table = 'recruitment_applications'
