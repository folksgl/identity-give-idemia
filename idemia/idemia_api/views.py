""" Views for Idemia API """
import logging
from rest_framework.generics import (
    CreateAPIView,
    RetrieveUpdateDestroyAPIView,
)
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models import EnrollmentRecord
from .serializers import EnrollmentRecordSerializer


class EnrollmentRecordCreate(CreateAPIView):
    """ Create EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer

    def create(self, request, *args, **kwargs):
        """ Create an enrollment record. POST to idemia /pre-enrollments endpoint """
        response = super().create(request, *args, **kwargs)
        if response.status_code == status.HTTP_201_CREATED:
            logging.info("Record Created -- POST to idemia /pre-enrollments")
        return response


class EnrollmentRecordDetail(RetrieveUpdateDestroyAPIView):
    """ Perform read, update, delete operations on EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer

    def retrieve(self, request, *args, **kwargs):
        """ Retrieve an enrollment record with the specified uuid """
        response = super().retrieve(request, *args, **kwargs)
        if response.status_code == status.HTTP_200_OK:
            logging.info("Record Retrieved - GET on idemia /pre-enrollments/UEID")
            logging.info("Call update() if status has changed")
        return response

    def update(self, request, *args, **kwargs):
        """ Update an enrollment record """
        response = super().update(request, *args, **kwargs)
        if response.status_code == status.HTTP_200_OK:
            logging.info("Record Updated")
        return response

    def destroy(self, request, *args, **kwargs):
        """ Delete an enrollment record """
        response = super().destroy(request, *args, **kwargs)
        if response.status_code == status.HTTP_204_NO_CONTENT:
            logging.info("Record Deleted")
        return response


@api_view(http_method_names=["GET"])
def location_view(_request, zipcode):
    """ Exposes the /locations idemia UEP endpoint """
    logging.info("Calling Idemia /locations endpoint with zipcode: %s", zipcode)

    # Dummy location info (taken from idemia api response documentation)
    location_list = [
        {
            "externalId": "5300155",
            "name": "Abilene, TX-Pine St",
            "phoneNumber": None,
            "timeZone": "America/Chicago",
            "address": {
                "building": "IdentoGO",
                "city": "Abilene",
                "addressLine2": None,
                "postalCode": "79601-5911",
            },
            "details": "",
            "geocode": {"latitude": 32.4509, "longitude": -99.73221},
            "hours": "",
            "programAvailability": [],
        },
        {
            "externalId": "5300173",
            "name": "Abilene, TX-S Willis St",
            "phoneNumber": None,
            "timeZone": "America/Chicago",
            "address": {
                "building": "IdentoGO",
                "city": "Abilene",
                "addressLine2": None,
                "postalCode": "79605-1734",
            },
            "details": "",
            "geocode": {"latitude": 32.45019, "longitude": -99.76409},
            "hours": "",
            "programAvailability": [],
        },
    ]
    return Response(location_list)
