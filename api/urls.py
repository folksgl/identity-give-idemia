""" Define URLs for the Django application """
from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from . import views

# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
    path("locations/<zipcode>", views.location_view, name="locations"),
    path("enrollment/", views.EnrollmentRecordCreate.as_view(), name="enrollment"),
    path(
        "enrollment/<uuid:pk>",
        views.EnrollmentRecordDetail.as_view(),
        name="enrollment-record",
    ),
]
