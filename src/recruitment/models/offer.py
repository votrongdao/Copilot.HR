from django.db import models
from .base import BaseModel
from .application import Application

class Offer(BaseModel):
    application = models.ForeignKey(Application, on_delete=models.CASCADE, related_name='offers')
    recruiter_id = models.UUIDField()
    status = models.CharField(max_length=50, default='DRAFT')
    offer_details = models.JSONField(default=dict)

    class Meta:
        db_table = 'recruitment_offers'
