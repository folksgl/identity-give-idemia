""" Serializers for the Idemia API """
from rest_framework import serializers
from .models import EnrollmentRecord


class EnrollmentRecordSerializer(serializers.ModelSerializer):
    """ Serializer for EnrollmentRecord objects """

    record_csp_id = serializers.CharField(read_only=True)
    record_idemia_ueid = serializers.CharField(read_only=True)

    class Meta:
        """ EnrollmentRecordSerializer metadata """

        model = EnrollmentRecord
        fields = "__all__"
        lookup_field = "record_csp_uuid"


class EnrollmentRecordCreateSerializer(EnrollmentRecordSerializer):
    """ Serializer for EnrollmentRecord objects when they are created """

    # added fields from request object. Not stored in database
    firstName = serializers.CharField()
    lastName = serializers.CharField()
