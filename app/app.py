from chalice import Chalice

app = Chalice(app_name="ipp-idemia")


@app.route("/enrollment", methods=["POST"])
def enrollment_register():
    data = app.current_request.json_body

    return {"hello": "world"}


@app.route("/locations", methods=["GET"])
def locations_get():
    return {"locations": "none"}


@app.route("/enrollment", methods=["GET"])
def status_get():
    return {"status": "empty"}


@app.route("/enrollment", methods=["PUT"])
def status_put():
    return {"hello": "world"}
