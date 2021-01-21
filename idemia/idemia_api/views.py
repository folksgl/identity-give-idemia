""" Views for Idemia API """
import logging
from rest_framework.generics import (
    CreateAPIView,
    RetrieveUpdateDestroyAPIView,
)
from rest_framework import status
from .models import EnrollmentRecord
from .serializers import EnrollmentRecordSerializer


class EnrollmentRecordCreate(CreateAPIView):
    """ Create EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer

    def create(self, request, *args, **kwargs):
        response = super().create(request, *args, **kwargs)
        if response.status_code == status.HTTP_201_CREATED:
            logging.info("Record Created")
        return response


class EnrollmentRecordDetail(RetrieveUpdateDestroyAPIView):
    """ Perform read, update, delete operations on EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer

    def retrieve(self, request, *args, **kwargs):
        response = super().retrieve(request, *args, **kwargs)
        if response.status_code == status.HTTP_200_OK:
            logging.info("Record Retrieved")
        return response

    def update(self, request, *args, **kwargs):
        response = super().update(request, *args, **kwargs)
        if response.status_code == status.HTTP_200_OK:
            logging.info("Record Updated")
        return response

    def destroy(self, request, *args, **kwargs):
        response = super().destroy(request, *args, **kwargs)
        if response.status_code == status.HTTP_204_NO_CONTENT:
            logging.info("Record Deleted")
        return response
