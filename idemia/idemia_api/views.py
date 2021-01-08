""" Views for Idemia API """
from rest_framework import viewsets
from .serializers import EnrollmentRecordSerializer
from .models import EnrollmentRecord


class EnrollmentRecordViewSet(viewsets.ModelViewSet):
    queryset = EnrollmentRecord.objects.all().order_by("record_uuid")
    serializer_class = EnrollmentRecordSerializer
