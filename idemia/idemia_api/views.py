""" Views for Idemia API """
import logging
import uuid
import requests
from django.conf import settings
from rest_framework.generics import (
    CreateAPIView,
    RetrieveUpdateDestroyAPIView,
)
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models import EnrollmentRecord
from .serializers import EnrollmentRecordSerializer


def log_transaction():
    """
    Log a transaction to the transaction logging microservice.
    Returns True if the logging attempt was successful.
    """
    logging.info("Logging a transaction to /transaction")
    if settings.DEBUG:
        logging.debug("Skipping transaction logging while in debug mode")
        return True  # Skip sending a transaction log in debug mode
    try:
        service_url = "https://identity-give-transaction-logging.app.cloud.gov"
        transaction_url = f"{service_url}/transaction/"
        payload = {
            "record_uuid": uuid.uuid4(),
            "service_type": "PROOFING SERVICE",
            "customer": "test_customer",
            "csp": "test_csp",
            "cost": 0,
            "result": "test_result",
        }

        response = requests.post(transaction_url, data=payload)
        response.raise_for_status()  # Raises HTTPError, if one occurred.
        return True
    except requests.exceptions.RequestException as error:
        logging.error("Request raised exception: %s", error)

    return False


class EnrollmentRecordCreate(CreateAPIView):
    """ Create EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer

    def create(self, request, *args, **kwargs):
        """ Create an enrollment record. POST to idemia /pre-enrollments endpoint """
        if log_transaction():
            response = super().create(request, *args, **kwargs)
            if response.status_code == status.HTTP_201_CREATED:
                logging.info("Record Created -- POST to idemia /pre-enrollments")
        else:
            response = Response(
                {"message": "Transaction logging failed. Aborting.."},
                status=status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
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

    # Dummy location info
    location_list = [
        {
            "title": "IdentoGO - TSA PreCheck&#8482",
            "address": "1 Saarinen Circle",
            "address2": "IAD International Airport",
            "city": "Sterling",
            "state": "VA",
            "postalCode": "20166-7547",
            "distance": "10.452655457551472",
            "hours": "Monday-Friday: 8:00 AM - 9:30 AM & 9:45 AM - 11:30 AM & 12:00 PM - 2:00 PM & 2:15 PM - 4:00 PM",
            "phone": "855-787-2227",
            "geocode": {"latitude": "38.952809", "longitude": "-77.447961"},
        },
        {
            "title": "IdentoGO TSA PreCheck&#8482 Enrollment at Staples",
            "address": "8387 Leesburg Pike",
            "address2": "Ste C",
            "city": "Vienna",
            "state": "VA",
            "postalCode": "22182-2420",
            "distance": "10.452655457551472",
            "hours": "Monday-Friday: 10:00 AM - 12:00 PM & 1:00 PM - 5:00 PM",
            "phone": "703-883-0011",
            "geocode": {"latitude": "38.921954", "longitude": "-77.236917"},
        },
        {
            "title": "IdentoGO - TSA PreCheck&#8482, TWIC, HAZMAT",
            "address": "1968 Gallows Rd",
            "address2": "VA DMV-Tyson's Corner",
            "city": "Vienna",
            "state": "VA",
            "postalCode": "22182-3909",
            "distance": "20.51593994774416",
            "hours": "Monday-Friday: 8:00 AM - 1:00 PM & 2:00 PM - 4:30 PM Saturday: 8:00 AM - 12:00 PM",
            "phone": "807-497-7100",
            "geocode": {"latitude": "38.910709", "longitude": "-77.225463"},
        },
        {
            "title": "IdentoGO TSA PreCheck&#8482 Enrollment at Staples",
            "address": "9890 Liberia Ave",
            "address2": "",
            "city": "Manassas",
            "state": "VA",
            "postalCode": "20110-5836",
            "distance": "24.29308762203185",
            "hours": "Monday-Thursday: 10:00 AM - 12:00 PM & 1:00 PM - 6:00 PM",
            "phone": "877-783-4187",
            "geocode": {"latitude": "38.743717", "longitude": "-77.451883"},
        },
        {
            "title": "IdentoGO - State Agency Enrollment",
            "address": "3139 Duke St",
            "address2": "",
            "city": "Alexandria",
            "state": "VA",
            "postalCode": "22314-4518",
            "distance": "30.81106117961712",
            "hours": "Monday-Thursday: 8:00 AM - 1:00 PM & 1:30 PM - 4:30 PM Friday: 8:00 AM - 1:00 PM & 1:30 PM - 4:00 PM",
            "phone": "877-783-4187",
            "geocode": {"latitude": "38.808868", "longitude": "-77.084946"},
        },
    ]

    return Response(location_list)
