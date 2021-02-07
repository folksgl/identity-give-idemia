"""
migrations.py implements the 'Migrate Frequently' section of
https://docs.huihoo.com/cloudfoundry/documentation/devguide/services/migrate-db.html
"""
import logging
import subprocess
from cfenv import AppEnv

ENV = AppEnv()

# Only allow the 0th instance of the application to run the migration scripts on the
# database. When deploying there will always be at least 1 application instance.
if ENV.index == 0:
    logging.warning("Instance index 0 started -- running migrations script")
    subprocess.call(["python", "manage.py", "migrate"])
    logging.warning("Migrations complete")
