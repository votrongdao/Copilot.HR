from rest_framework.test import APITestCase
from django.urls import reverse
from rest_framework import status
from django.conf import settings
import os

def reset_json_db():
    data_file = os.path.join(settings.BASE_DIR, 'data.json')
    if os.path.exists(data_file):
        os.remove(data_file)

class RequirementAPITests(APITestCase):
    
    def setUp(self):
        reset_json_db()

    def test_get_requirements_list(self):
        url = reverse('requirements-list')
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

        self.assertTrue(response.json().get('success'))
        self.assertEqual(response.json().get('data'), [])
        
    def test_create_requirement(self):
        url = reverse('requirements-list')
        data = {
            "department_id": "123e4567-e89b-12d3-a456-426614174000",
            "position_id": "123e4567-e89b-12d3-a456-426614174001",
            "hiring_manager_id": "123e4567-e89b-12d3-a456-426614174002",
            "priority": "HIGH",
            "employment_type": "FULL_TIME",
        }
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertTrue(response.json().get('success'))

class JobAPITests(APITestCase):

    def setUp(self):
        reset_json_db()

    def test_get_jobs_list(self):
        url = reverse('jobs-list')
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertTrue(response.json().get('success'))

    def test_publish_job(self):
        url = reverse('jobs-publish', kwargs={'pk': '123e4567-e89b-12d3-a456-426614174000'})
        response = self.client.post(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
