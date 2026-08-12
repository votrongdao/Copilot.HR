from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from recruitment.services import RequirementService, JobService, ApplicationService

class RequirementController(APIView):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.service = RequirementService()

    def get(self, request):
        items = self.service.get_all()
        return Response({"success": True, "data": items}, status=status.HTTP_200_OK)

    def post(self, request):
        item = self.service.create(request.data)
        return Response({"success": True, "data": item}, status=status.HTTP_201_CREATED)

class RequirementDetailController(APIView):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.service = RequirementService()

    def get(self, request, pk):
        item = self.service.get_by_id(pk)
        return Response({"success": True, "data": item}) if item else Response(status=404)

    def patch(self, request, pk):
        item = self.service.update(pk, request.data)
        return Response({"success": True, "data": item}) if item else Response(status=404)

    def delete(self, request, pk):
        success = self.service.delete(pk)
        return Response(status=status.HTTP_204_NO_CONTENT) if success else Response(status=400)

class JobController(APIView):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.service = JobService()

    def get(self, request):
        items = self.service.get_all()
        return Response({"success": True, "data": items}, status=status.HTTP_200_OK)

    def post(self, request):
        item = self.service.create(request.data)
        return Response({"success": True, "data": item}, status=status.HTTP_201_CREATED)

class JobDetailController(APIView):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.service = JobService()

    def get(self, request, pk):
        item = self.service.get_by_id(pk)
        return Response({"success": True, "data": item}) if item else Response(status=404)

    def patch(self, request, pk):
        item = self.service.update(pk, request.data)
        return Response({"success": True, "data": item}) if item else Response(status=404)

    def delete(self, request, pk):
        result = self.service.delete(pk)

        if result.get("error") == "Not Found":
            return Response({"success": False, "message": "Job not found"}, status = status.HTTP_404_NOT_FOUND)

        if result.get("error") == "LOCKED":
            return Response({"success": False, "message": result.get("msg")}, status = status.HTTP_400_BAD_REQUEST)

        return Response(status = status.HTTP_204_NO_CONTENT)


class JobPublishController(APIView):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.service = JobService()

    def post(self, request, pk):
        result = self.service.publish_job(pk, request.data.get("intergration_ids"))

        if result.get("error") == "Not Found":
            return Response({"Success": False, "message": "Job not found"}, status = status.HTTP_404_NOT_FOUND)

        if result.get("error") == "Missing title or requirement_id":
            return Response({"Success": False, "message": "Missing title or requirement"}, status = status.HTTP_400_BAD_REQUEST)

        return Response(result, status = status.HTTP_200_OK)

class ApplicationController(APIView):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.service = ApplicationService()

    def post(self, request):
        result = self.service.create(request.data)

        if result.get("error") == "MISSING_FIELDS":
            return Response({"success" : False, "message": result.get("msg")}, status = status.HTTP_400_BAD_REQUEST)
        
        return Response(result, status = status.HTTP_201_CREATED)