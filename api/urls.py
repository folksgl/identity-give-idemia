""" Define URLs for the Django application """
from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from rest_framework import status
from . import views
from drf_yasg.utils import swagger_auto_schema
from .serializers import EnrollmentRecordCreateSerializer, EnrollmentRecordSerializer

decorated_enrollmentcreate_view = swagger_auto_schema(
    method="post",
    request_body=EnrollmentRecordCreateSerializer,
    responses={status.HTTP_201_CREATED: EnrollmentRecordSerializer},
)(views.EnrollmentRecordCreate.as_view())

# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
    path("locations/<zipcode>", views.location_view, name="locations"),
    path("enrollment/", decorated_enrollmentcreate_view, name="enrollment"),
    path(
        "enrollment/<uuid:pk>",
        views.EnrollmentRecordDetail.as_view(),
        name="enrollment-record",
    ),
]
