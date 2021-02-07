"""
migrations.py implements the 'Migrate Frequently' section of
https://docs.huihoo.com/cloudfoundry/documentation/devguide/services/migrate-db.html
"""
import logging
import subprocess
from cfenv import AppEnv

ENV = AppEnv()

if ENV.index == 0:
    logging.warning("Instance index 0 started -- running migrations script")
    subprocess.call(["python", "manage.py", "migrate"])
    logging.warning("Migrations complete")
