from django.db import models
from .base import BaseModel
from .application import Application

class Interview(BaseModel):
    application = models.ForeignKey(Application, on_delete=models.CASCADE, related_name='interviews')
    interviewer_id = models.UUIDField()
    round = models.CharField(max_length=50)
    status = models.CharField(max_length=50, default='SCHEDULED')
    date_from = models.DateTimeField(null=True, blank=True)
    date_to = models.DateTimeField(null=True, blank=True)

    class Meta:
        db_table = 'recruitment_interviews'
