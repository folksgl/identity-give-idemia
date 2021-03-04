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

    record_csp_id = models.CharField(max_length=50)
    record_csp_uuid = models.UUIDField()
    record_idemia_ueid = models.CharField(max_length=10)
    record_status = models.CharField(
        max_length=20,
        choices=EnrollmentStatus.choices,
        default=EnrollmentStatus.PENDING,
    )
    creation_date = models.DateTimeField(auto_now_add=True)
    last_modified = models.DateTimeField(auto_now=True)

    class Meta:
        """ EnrollmentRecord Model metadata """

        ordering = ["-creation_date"]
        unique_together = ("record_csp_uuid", "record_csp_id")
