""" Views for Idemia API """
import logging
import requests
from django.conf import settings
from rest_framework.generics import (
    CreateAPIView,
    RetrieveUpdateDestroyAPIView,
)
from rest_framework import status
from rest_framework.exceptions import APIException
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models import EnrollmentRecord, EnrollmentStatus
from .serializers import EnrollmentRecordSerializer

import random
import string


class TransactionServiceUnavailable(APIException):
    """ Thrown during errors contacting the transaction logging service """

    status_code = 503
    default_detail = (
        "Transaction logging service temporarily unavailable, try again later."
    )
    default_code = "service_unavailable"


def log_transaction():
    """
    Log a transaction to the transaction logging microservice.
    Returns True if the logging attempt was successful.
    """
    if settings.DEBUG:
        logging.debug("Skipping transaction logging while in debug mode")
        response = requests.Response()
        response.status_code = 201
        return response  # Skip sending a transaction log in debug mode

    logging.info("Logging a transaction to /transaction")
    transaction_url = (
        "http://identity-give-transaction-log.apps.internal:8080/transaction/"
    )
    payload = {
        "service_type": "PROOFING SERVICE",
        "customer": "test_customer",
        "csp": "test_csp",
        "cost": 0,
        "result": "test_result",
    }

    try:
        response = requests.post(transaction_url, data=payload)
        response.raise_for_status()  # Raises HTTPError, if one occurred.
    except requests.exceptions.RequestException as error:
        logging.error("Request raised exception: %s", error)

    return response


class EnrollmentRecordCreate(CreateAPIView):
    """ Create EnrollmentRecord objects """

    queryset = EnrollmentRecord.objects.all()
    serializer_class = EnrollmentRecordSerializer

    def perform_create(self, serializer):
        """ Custom logic upon creating an enrollment record """
        # HTTP_X_CONSUMER_CUSTOM_ID is filtered by API Gateway so no validation required
        csp_id = self.request.META["HTTP_X_CONSUMER_CUSTOM_ID"]
        # Get UEID from Idemia UEP API
        ueid = "".join(random.choices(string.ascii_uppercase + string.digits, k=10))
        log_response = log_transaction()
        if log_response.status_code == status.HTTP_201_CREATED:
            serializer.save(record_idemia_ueid=ueid, record_csp_id=csp_id)
            logging.info("Record Created -- POST to idemia /pre-enrollments")
        else:
            raise TransactionServiceUnavailable()


class EnrollmentRecordDetail(RetrieveUpdateDestroyAPIView):
    """ Perform read, update, delete operations on EnrollmentRecord objects """

    serializer_class = EnrollmentRecordSerializer
    lookup_field = "record_csp_uuid"

    def get_queryset(self):
        # HTTP_X_CONSUMER_CUSTOM_ID is filtered by API Gateway so no validation required
        return EnrollmentRecord.objects.filter(
            record_csp_id=self.request.META["HTTP_X_CONSUMER_CUSTOM_ID"]
        )

    def get(self, request, *args, **kwargs):
        """ Custom logic upon retrieving an enrollment record """
        response = self.retrieve(request, *args, **kwargs)
        if response.status_code == status.HTTP_200_OK:
            logging.info("Record Retrieved - GET on idemia /pre-enrollments/UEID")
            logging.info("Call self.update() if status has changed")
        return response

    def perform_update(self, serializer):
        """ Custom logic upon updating an enrollment record """
        logging.info("Record Updated")
        serializer.save()

    def perform_destroy(self, instance):
        """ Custom logic upon deleting an enrollment record """
        logging.info("Record Deleted")
        instance.delete()


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
