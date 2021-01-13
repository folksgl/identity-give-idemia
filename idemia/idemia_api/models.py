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

    first_name = models.CharField(max_length=60)
    last_name = models.CharField(max_length=60)
    record_uuid = models.UUIDField(primary_key=True)
    record_status = models.CharField(
        max_length=20,
        choices=EnrollmentStatus.choices,
        default=EnrollmentStatus.PENDING,
    )
    creation_date = models.DateTimeField(auto_now_add=True)
    last_modified = models.DateTimeField(auto_now=True)

    class Meta:
        ordering = ["-creation_date"]

    @property
    def status(self):
        """ Allow 'get' of the objects 'record_status' field """
        return self.record_status

    @status.setter
    def update_enrollment_status(self, new_status: EnrollmentStatus):
        """ Start the enrollment process for this record """
        self._status = new_status

    @property
    def uuid(self):
        """ Allow reading string representations of the uuid """
        return str(self.record_uuid)

    def __str__(self):
        return f"{self.record_uuid}: {self.record_status}"
