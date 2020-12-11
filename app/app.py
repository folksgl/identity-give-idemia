""" Sample Chalice "hello world" application """
import json
from chalice import Chalice

with open(".chalice/config.json") as config_file:
    CONFIG = json.load(config_file)
    print(json.dumps(CONFIG))

if "app_name" not in CONFIG:
    raise KeyError("No 'app_name' configured in app/.chalice/config.json")

APP_NAME = CONFIG.get("app_name")

# Chalice currently requires app.py to have 'app' (lowercase) available
app = Chalice(app_name=APP_NAME)


@app.route("/enrollment", methods=["POST"])
def enrollment_register():
    """
    Pre-Enrollment Registration Function. Receives an enrollment applicant and registers said
    applicant with the Idemia IPP service.
    """
    return {"hello": "world"}


@app.route("/locations", methods=["GET"])
def locations_get():
    """
    Locations Function. Receives a zip code and returns a list of local IPP locations.
    """
    return {"locations": "none"}


@app.route("/enrollment", methods=["GET"])
def status_get():
    """
    Fetch Status Function. Returns a user's status based on a given query parameter UUID.
    """
    return {"status": "empty"}


@app.route("/enrollment", methods=["PUT"])
def status_put():
    """
    Update Status Function. Receives a UEID as a query parameter and a status in the request body.
    Updates the user's status corresponding to the UEID to the new status provided.
    """
    return {"hello": "world"}
