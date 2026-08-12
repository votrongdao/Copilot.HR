import uuid
from django.db import models

class Application(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    candidate_id = models.UUIDField(null=True, blank=True)
    job_id = models.UUIDField(null=True, blank=True)
    owner_recruiter_id = models.UUIDField(null=True, blank=True)

    source = models.CharField(max_length=100, null=True, blank=True)
    stage = models.CharField(max_length=50, null=True, blank=True)
    ai_score = models.DecimalField(max_digits=5, decimal_places=2, null=True, blank=True)
    status = models.CharField(max_length=50, null=True, blank=True)

    class Meta:
        db_table = 'applications'
