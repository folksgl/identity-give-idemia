""" Test the location functionality of the idemia microservice """
from django.urls import reverse
from rest_framework.test import APITestCase
from rest_framework import status


class LocationsTest(APITestCase):
    """ Test the allowable HTTP methods on the idemia location microservice """

    def test_locations(self):
        """ Ensure that the /locations endpoint returns location data """
        url = reverse("locations")
        response = self.client.get(url)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertTrue(isinstance(response.data, list))
        self.assertTrue(response.data)  # list not empty

        location_data = response.data[0]
        data_keys = [
            "externalId",
            "name",
            "phoneNumber",
            "timeZone",
            "address",
            "details",
            "geocode",
            "hours",
            "programAvailability",
        ]
        self.assertEqual(list(location_data.keys()), data_keys)
