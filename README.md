Idemia In-Person-Proofing Microservice
=================
## Table of Contents
- [Overview](#overview)
- [Installation](#installation)
    - [Setting Up](#setting-up-your-environment)
- [Deploying the application](#deploying-the-application-to-cloud.gov)

## Overview
The Idemia microservice is a Python Django application that uses the Django Rest Framework to expose an API for in-person-proofing functions to GIVE.

## Installation

### Setting up a development environment
To set up your environment, follow these steps (or the equivalent steps if not using a bash-like terminal):
```sh
git clone https://github.com/18F/identity-give-ipp-idemia
cd identity-give-ipp-idemia
python3 -m venv venv
source venv/bin/activate
python3 -m pip install -r requirements.txt -r requirements-dev.txt
pre-commit install
```

Installation of dependencies and commit hooks should be installed and ready to go now. To run the application locally:
```sh
cd idemia
python manage.py runserver
```

### Deploying the application to Cloud.gov
All deployments require having the correct Cloud.gov credentials in place. If you haven't already, visit [Cloud.gov](https://cloud.gov) and set up your account and CLI.
*manifest.yml* file needed for cloud.gov deployments has not been provided at this time.

