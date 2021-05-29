""" Run basic CRUD tests on EnrollmentRecord objects """
import uuid
from django.urls import reverse
from django.test import TestCase, Client
from rest_framework import status
from api.models import EnrollmentStatus


def generate_enrollment_record_data() -> dict:
    """Helper method for generating data for an EnrollmentRecord"""
    return {
        "csp_user_uuid": uuid.uuid4(),
        "first_name": "Bob",
        "last_name": "Testington",
    }


def generate_header(consumer_id) -> dict:
    """Helper method for generating enrollment record request headers"""
    return {"HTTP_X_CONSUMER_CUSTOM_ID": consumer_id}


def create_enrollment_record(client):
    """
    Perform a POST operation to the enrollment endpoint. This operation will
    serve as the base for many other tests, and will intentionally generate
    failures if the operation results in anything other than a 201 response
    """
    url = reverse("enrollment")
    record_data = generate_enrollment_record_data()
    response = client.post(url, record_data)

    return (response, record_data)


class EnrollmentAllowedMethodTest(TestCase):
    """Test the allowable HTTP methods on the idemia API endpoints"""

    def test_allowed_enrollment_methods(self):
        """Ensure that only POST operations are allowed on the enrollment endpoint"""
        url = reverse("enrollment")
        response = self.client.options(url)
        actions = response.data["actions"]
        allowed_methods = list(actions.keys())

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(allowed_methods, ["POST"])


class EnrollmentRecordCRUDTest(TestCase):
    """Test crud operations on EnrollmentRecord objects"""

    def setUp(self):
        self.client = Client(HTTP_X_CONSUMER_CUSTOM_ID="consumera")

    def test_post_enrollment(self):
        """Test basic enrollment record creation"""
        response, _record_data = create_enrollment_record(self.client)

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_get_enrollment(self):
        """Create a user, then test the 'get' operation on that user"""
        _response, record_data = create_enrollment_record(self.client)

        url = reverse("enrollment-record", args=[record_data["csp_user_uuid"]])
        get_response = self.client.get(url)

        self.assertEqual(get_response.status_code, status.HTTP_200_OK)

    def test_get_enrollment_badheader(self):
        """Create a user, then test the 'get' operation on that user with a bad header"""
        _response, record_data = create_enrollment_record(self.client)

        url = reverse("enrollment-record", args=[record_data["csp_user_uuid"]])
        request_headers = generate_header("consumerb")
        get_response = self.client.get(url, **request_headers)

        self.assertEqual(get_response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete_enrollment(self):
        """Delete a user that was created"""
        _response, record_data = create_enrollment_record(self.client)

        url = reverse("enrollment-record", args=[record_data["csp_user_uuid"]])
        delete_response = self.client.delete(url)
        self.assertEqual(delete_response.status_code, status.HTTP_204_NO_CONTENT)

        get_response = self.client.get(url)
        self.assertEqual(get_response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete_enrollment_badheader(self):
        """Try to delete a user that was created using a bad header"""
        _response, record_data = create_enrollment_record(self.client)

        url = reverse("enrollment-record", args=[record_data["csp_user_uuid"]])
        request_headers = generate_header("consumerb")
        delete_response = self.client.delete(url, **request_headers)
        self.assertEqual(delete_response.status_code, status.HTTP_404_NOT_FOUND)

        get_response = self.client.get(url)
        self.assertEqual(get_response.status_code, status.HTTP_200_OK)

    def test_put_enrollment(self):
        """Modify an existing enrollment record"""
        _response, record_data = create_enrollment_record(self.client)
        url = reverse("enrollment-record", args=[record_data["csp_user_uuid"]])

        new_status = EnrollmentStatus.FAILED
        record_data["status"] = new_status

        put_response = self.client.put(
            url, record_data, content_type="application/json"
        )
        get_response = self.client.get(url)

        self.assertEqual(put_response.status_code, status.HTTP_200_OK)
        self.assertEqual(get_response.status_code, status.HTTP_200_OK)
        self.assertEqual(get_response.data["status"], new_status)

    def test_put_enrollment_badheader(self):
        """Try to modify an existing enrollment record using a bad header"""
        response, record_data = create_enrollment_record(self.client)
        url = reverse("enrollment-record", args=[record_data["csp_user_uuid"]])
        request_headers = generate_header("consumerb")

        old_status = response.data["status"]
        new_status = EnrollmentStatus.FAILED
        record_data["status"] = new_status

        put_response = self.client.put(url, record_data, **request_headers)
        get_response = self.client.get(url, **request_headers)

        self.assertEqual(put_response.status_code, status.HTTP_404_NOT_FOUND)

        get_response = self.client.get(url)

        self.assertEqual(get_response.data["status"], old_status)

    def test_put_enrollment_ueid_edit_attempt(self):
        """Attempt to modify an existing enrollment record's ueid"""
        _response, record_data = create_enrollment_record(self.client)
        url = reverse("enrollment-record", args=[record_data["csp_user_uuid"]])

        new_ueid = "ASDFGHJKLA"
        record_data["idemia_ueid"] = new_ueid

        put_response = self.client.put(
            url, record_data, content_type="application/json"
        )
        get_response = self.client.get(url)

        self.assertEqual(put_response.status_code, status.HTTP_200_OK)
        self.assertEqual(get_response.status_code, status.HTTP_200_OK)
        self.assertNotEqual(get_response.data["idemia_ueid"], new_ueid)
