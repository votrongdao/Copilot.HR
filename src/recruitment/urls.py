from django.urls import path
from recruitment.controllers import (
    RequirementController, RequirementDetailController,
    JobController, JobDetailController, JobPublishController, ApplicationController
)

urlpatterns = [
    path('requirements/', RequirementController.as_view(), name='requirements-list'),
    path('requirements/<uuid:pk>/', RequirementDetailController.as_view(), name='requirements-detail'),
    
    path('jobs/', JobController.as_view(), name='jobs-list'),
    path('jobs/<uuid:pk>/', JobDetailController.as_view(), name='jobs-detail'),
    path('jobs/<uuid:pk>/publish/', JobPublishController.as_view(), name='jobs-publish'),
    path('applications/', ApplicationController.as_view(), name='applications-list'),
]
