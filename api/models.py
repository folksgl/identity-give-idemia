""" Models for the Idemia microservice """
from django.db import models


class EnrollmentStatus(models.TextChoices):
    """ Status states for EnrollmentRecord objects """

    PENDING = "PENDING"
    IN_PROGRESS = "IN PROGRESS"
    SUCCESSFUL = "SUCCESSFUL"
    FAILED = "FAILED"


class EnrollmentRecord(models.Model):
    """ EnrollmentRecord objects hold information representing a single enrollment record """

    # This vaue is captured from the request headers, and comes from the
    # custom_id stored in the Kong consumer for this CSP
    csp_id = models.CharField(max_length=16)

    # The uuid for this user from the CSP making the request
    csp_user_uuid = models.UUIDField()

    # The ueid for this request within the Idemia system
    idemia_ueid = models.CharField(max_length=10)

    # The status of the enrollment (within GIVE - does not necessarily reflect
    # the most up-to-date status from Idemia at all times)
    status = models.CharField(
        max_length=20,
        choices=EnrollmentStatus.choices,
        default=EnrollmentStatus.PENDING,
    )
    creation_date = models.DateTimeField(auto_now_add=True)
    last_modified = models.DateTimeField(auto_now=True)

    class Meta:
        """ EnrollmentRecord Model metadata """

        ordering = ["-creation_date"]
        unique_together = ("csp_user_uuid", "csp_id")
