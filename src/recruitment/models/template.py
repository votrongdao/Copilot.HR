from django.db import models
from .base import BaseModel

class RecruitmentTemplate(BaseModel):
    TEMPLATE_TYPES = [
        ('JOB_DESCRIPTION', 'Job Description'),
        ('EMAIL', 'Email'),
        ('OFFER', 'Offer'),
    ]
    name = models.CharField(max_length=255)
    type = models.CharField(max_length=50, choices=TEMPLATE_TYPES)
    status = models.CharField(max_length=50, default='ACTIVE')
    content = models.TextField()

    class Meta:
        db_table = 'recruitment_templates'
