# Generated by Django 3.1.5 on 2021-01-06 16:59

from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
        ("idemia_api", "0002_auto_20210106_1602"),
    ]

    operations = [
        migrations.RenameField(
            model_name="enrollmentrecord",
            old_name="_first_name",
            new_name="first_name",
        ),
        migrations.RenameField(
            model_name="enrollmentrecord", old_name="_last_name", new_name="last_name",
        ),
        migrations.RenameField(
            model_name="enrollmentrecord", old_name="_status", new_name="record_status",
        ),
        migrations.RenameField(
            model_name="enrollmentrecord", old_name="_uuid", new_name="record_uuid",
        ),
        migrations.AddField(
            model_name="enrollmentrecord",
            name="creation_date",
            field=models.DateTimeField(
                auto_now_add=True, default=django.utils.timezone.now
            ),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name="enrollmentrecord",
            name="last_modified",
            field=models.DateTimeField(auto_now=True),
        ),
    ]
