""" Tests for app.py """
from chalice.test import Client
from pytest import fixture
from app import app


@fixture(name="client_fixture")
def test_client():
    """ Test fixture for creating a chalice Client """
    with Client(app) as client:
        yield client


def test_enrollment_register_function(client_fixture):
    """ Ensure the enrollment function returns the correct stub """
    response = client_fixture.http.post("/enrollment")
    assert response.json_body == {"hello": "world"}


def test_locations_get_function(client_fixture):
    """ Ensure the location function returns the correct stub """
    response = client_fixture.http.get("/locations")
    assert response.json_body == {"locations": "none"}


def test_status_get_function(client_fixture):
    """ Ensure the get status function returns the correct stub """
    response = client_fixture.http.get("/enrollment")
    assert response.json_body == {"status": "empty"}


def test_status_put_function(client_fixture):
    """ Ensure the get update function returns the correct stub """
    response = client_fixture.http.put("/enrollment")
    assert response.json_body == {"hello": "world"}
