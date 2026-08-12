from rest_framework.test import APITestCase
from django.urls import reverse
from rest_framework import status

class RequirementAPITests(APITestCase):

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
            "hiring_quantity": 2,
        }
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertTrue(response.json().get('success'))

class JobAPITests(APITestCase):

    def test_get_jobs_list(self):
        url = reverse('jobs-list')
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertTrue(response.json().get('success'))

    def test_publish_job(self):
        url_create = reverse('jobs-list')
        res_create = self.client.post(url_create, {'title' : 'Backend Java'}, format='json')

        job_id = res_create.json()['data']['id']

        url_publish = reverse('jobs-publish', kwargs={'pk': job_id})
        res_400 = self.client.post(url_publish, format='json')

        self.assertEqual(res_400.status_code, status.HTTP_400_BAD_REQUEST)

        url_detail = reverse('jobs-detail', kwargs={'pk': job_id})
        self.client.patch(url_detail, {"title": "Frontend Developer", "requirement_id": "893e4567-e89b-12d3-a456-426614174099"}, format='json')

        res_success = self.client.post(url_publish, format='json')
        self.assertEqual(res_success.status_code, status.HTTP_200_OK)
        self.assertEqual(res_success.json()['data']['status'], 'Published')

    def test_delete_job(self):
        res_create = self.client.post(reverse('jobs-list'), {"title": "Frontend Developer", "requirement_id": "893e4567-e89b-12d3-a456-426614174099"}, format='json')
        job_id = res_create.json()['data']['id']
        url_detail = reverse('jobs-detail', kwargs={'pk': job_id})

        self.client.post(reverse('jobs-publish', kwargs={'pk': job_id}), format='json')
        res_del_fail = self.client.delete(url_detail)   

        self.assertEqual(res_del_fail.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(res_del_fail.json()['message'], 'Job is Published')

        ###
        res_draft = self.client.post(reverse('jobs-list'), {"title": "Draft Job"}, format='json')
        draft_id = res_draft.json()['data']['id']
        res_del_success = self.client.delete(reverse('jobs-detail', kwargs={'pk': draft_id}))
        self.assertEqual(res_del_success.status_code, status.HTTP_204_NO_CONTENT)

class ApplicationAPITests(APITestCase):
    def test_apply_cv(self):
        url = reverse('applications-list')
        
        res_fail = self.client.post(url, {"job_id": "123e4567-e89b-12d3-a456-426614174099"}, format='json')
        self.assertEqual(res_fail.status_code, status.HTTP_400_BAD_REQUEST)
        
        res_success = self.client.post(url, {
            "job_id": "123e4567-e89b-12d3-a456-426614174099", 
            "candidate_id": "123e4567-e89b-12d3-a456-426614174088",
            "status": "Hired"  
        }, format='json')
        
        self.assertEqual(res_success.status_code, status.HTTP_201_CREATED)
        self.assertEqual(res_success.json()['data']['status'], 'New')
        self.assertIsNotNone(res_success.json()['data'].get('ai_score'))
