""" Transaction Logging functionality """
import logging
import requests
from django.conf import settings
from rest_framework.exceptions import APIException

# TRANSACTION_URL = "http://localhost:8000/transaction/"
TRANSACTION_URL = "http://identity-give-transaction-log.apps.internal:8080/transaction/"

_DEBUG_RESPONSE = {"record_uuid": "fake-uuid"}


class TransactionServiceUnavailable(APIException):
    """ Thrown during errors contacting the transaction logging service """

    status_code = 503
    default_detail = "Transaction logging service temporarily unavailable."
    default_code = "service_unavailable"


def create_transaction(csp: str, cost=0, result="unknown") -> dict:
    """
    Log a transaction to the transaction logging microservice.
    Returns True if the logging attempt was successful.
    """
    if settings.DEBUG:
        logging.debug("Skipping transaction logging while in debug mode")
        return _DEBUG_RESPONSE  # Skip sending a transaction log in debug mode

    payload = {
        "service_type": "PROOFING SERVICE",
        "provider": "idemia",
        "csp": csp,
        "cost": cost,
        "result": result,
    }

    try:
        response = requests.post(TRANSACTION_URL, data=payload)
        response.raise_for_status()  # Raises HTTPError, if one occurred.
    except requests.exceptions.RequestException as error:
        logging.error("Request raised exception: %s", error)
        raise TransactionServiceUnavailable from error

    return response.json()


def update_transaction_result(record_uuid: str, result: str) -> dict:
    """ Update information in an existing transaction record """
    if settings.DEBUG:
        logging.debug("Skipping transaction logging update while in debug mode")
        return _DEBUG_RESPONSE  # Skip sending a transaction log in debug mode

    url = f"{TRANSACTION_URL}{record_uuid}/"

    try:
        response = requests.patch(url, data={"result": result})
        response.raise_for_status()  # Raises HTTPError, if one occurred.
    except requests.exceptions.RequestException as error:
        logging.error("Request raised exception: %s", error)
        raise TransactionServiceUnavailable from error

    return response.json()
