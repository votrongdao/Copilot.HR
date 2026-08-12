import uuid
from django.db import models

class Requirement(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    department_id = models.UUIDField(null=True, blank=True)
    position_id = models.UUIDField(null=True, blank=True)
    hiring_manager_id = models.UUIDField(null=True, blank=True)

    title = models.CharField(max_length=255, null=True, blank=True)
    hiring_quantity = models.IntegerField(null=True, blank=True)
    priority = models.CharField(max_length=50, null=True, blank=True)
    approval_status = models.CharField(max_length=50, null=True, blank=True)
    status = models.CharField(max_length=50, null=True, blank=True)

    class Meta:
        db_table = 'recruitment_requirements'
