""" Serializers for the Idemia API """
from rest_framework import serializers
from .models import EnrollmentRecord


class EnrollmentRecordSerializer(serializers.ModelSerializer):
    """ Serializer for EnrollmentRecord objects """

    class Meta:
        model = EnrollmentRecord
        fields = "__all__"
