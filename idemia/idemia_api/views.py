""" Views for Idemia API """
from rest_framework import generics
from .models import EnrollmentRecord
from .serializers import EnrollmentRecordSerializer


class EnrollmentRecordCreate(generics.CreateAPIView):
    """ Create and list EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer


class EnrollmentRecordDetail(generics.RetrieveUpdateDestroyAPIView):
    """ Perform read, update, delete operations on EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer
