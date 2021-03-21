""" Serializers for the Idemia API """
from rest_framework import serializers
from api.models import EnrollmentRecord


class EnrollmentRecordSerializer(serializers.ModelSerializer):
    """ Serializer for EnrollmentRecord objects """

    csp_id = serializers.CharField(read_only=True)
    idemia_ueid = serializers.CharField(read_only=True)

    class Meta:
        """ EnrollmentRecordSerializer metadata """

        model = EnrollmentRecord
        fields = "__all__"
        lookup_field = "csp_uuid"


class EnrollmentRecordCreateSerializer(EnrollmentRecordSerializer):
    """ Serializer for EnrollmentRecord objects when they are created """

    # added fields from request object. Not stored in database
    first_name = serializers.CharField()
    last_name = serializers.CharField()
    status = serializers.CharField(read_only=True)
