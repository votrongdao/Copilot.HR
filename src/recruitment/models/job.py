import uuid
from django.db import models

class Job(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    requirement_id = models.UUIDField(null=True, blank=True)
    recruiter_id = models.UUIDField(null=True, blank=True)
    
    title = models.CharField(max_length=255, null=True, blank=True)
    employment_type = models.CharField(max_length=50, null=True, blank=True)
    work_mode = models.CharField(max_length=50, null=True, blank=True)
    status = models.CharField(max_length=50, null=True, blank=True)

    class Meta:
        db_table = 'jobs'
