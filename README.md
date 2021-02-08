![Tests](https://github.com/18F/identity-give-ipp-idemia/workflows/Unit-Tests/badge.svg)
[![Maintainability](https://api.codeclimate.com/v1/badges/7a72205acec6d179707c/maintainability)](https://codeclimate.com/github/18F/identity-give-ipp-idemia/maintainability)
[![Code style: black](https://img.shields.io/badge/code%20style-black-000000.svg)](https://github.com/psf/black)

# Government Identity Verification Engine

## Idemia In-Person-Proofing Microservice

The Idemia microservice is a Python Django application that uses the Django Rest Framework to expose an API for in-person-proofing functions to GIVE.

## Table of Contents
- [Installation](#installation)
    - [Setting Up](#setting-up-your-environment)
- [Deploying the application](#deploying-the-application-to-cloud.gov)
- [API Endpoints](#api-endpoints)

## Pre-requisites
- [Python 3.9](https://www.python.org/)
- [CloudFoundry CLI](https://docs.cloudfoundry.org/cf-cli/)

## Building Locally

### Development Setup
To set up your environment, run the following commands (or the equivalent commands if not using a bash-like terminal):
```shell
# Clone the project
git clone https://github.com/18F/identity-give-ipp-idemia
cd identity-give-ipp-idemia
# Set up Python virtual environment
python3.9 -m venv .venv
source venv/bin/activate
# .venv\Scripts\Activate.ps1 on Windows
# Install dependencies and pre-commit hooks
python -m pip install -r requirements-dev.txt
pre-commit install
```

:warning: **If you are not able to install `psycopg2`**, please make sure you have `libpq-dev` installed on your system. For apt, use the following `sudo apt install -y libpq-dev`

### Required environment variables
The Django settings.py file for this project requires setting an environment variable: `SECRET_KEY`
Running the following in your shell should print a secret key that can be used.
```shell
python
import secrets
secrets.token_urlsafe()
exit()

```

Set the environment variable using *the entire output* (including quotes) from the printed secret
```shell
# BASH-like shells
export SECRET_KEY=<your-secret-here>
```
```powershell
# PowerShell
Env:SECRET_KEY=<your-secret-here>
```
Note: during development, it may also be helpful to add the `DEBUG` environment variable and setting it to the string `True`


### Running the application
After completing [development setup](#development-setup) and [environment variable setup](#required-environment-variables) you can run the application with:
```shell
python manage.py migrate
python manage.py collectstatic
python manage.py test --debug-mode
gunicorn -b 127.0.0.1:8080 idemia.wsgi
```

### Deploying the application to Cloud.gov (manually)
All deployments require having the correct Cloud.gov credentials in place. If you haven't already, visit [Cloud.gov](https://cloud.gov) and set up your account and CLI.

*manifest.yml* file contains the deployment configuration for cloud.gov, and expects a vars.yaml file that includes runtime variables referenced. For info, see [cloud foundry manifest files reference](https://docs.cloudfoundry.org/devguide/deploy-apps/manifest-attributes.html)

The application database must be deployed prior to the application, and can be deployed with the following commands:
```shell
cf create-service aws-rds <plan> ipp-idemia-db
```

*You must wait* until the database has completed provisioning to continue with the deployment. Wait for the `status` field of `cf service ipp-idemia-db` to change from `create in progress` to `create succeeded`.
```shell
watch -n 15 cf service ipp-idemia-db
```

After the database has come up, running `cf push --vars-file vars.yaml --var SECRET_KEY=$SECRET_KEY`.

### API Endpoints
#### /enrollment
Idemia pre-enrollment API functionality.

#### /locations
Exposes in-person proofing locations via the idemia UEP locations API
