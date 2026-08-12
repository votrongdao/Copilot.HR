from django.db import models
from .base import BaseModel
from .application import Application

class Schedule(BaseModel):
    application = models.ForeignKey(Application, on_delete=models.CASCADE, related_name='schedules')
    interviewer_id = models.UUIDField()
    status = models.CharField(max_length=50, default='SCHEDULED')
    date_from = models.DateTimeField(null=True, blank=True)
    date_to = models.DateTimeField(null=True, blank=True)

    class Meta:
        db_table = 'recruitment_schedules'
