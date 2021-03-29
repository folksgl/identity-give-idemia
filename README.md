![Tests](https://github.com/18F/identity-give-idemia/workflows/Unit-Tests/badge.svg)
[![Maintainability](https://api.codeclimate.com/v1/badges/7a72205acec6d179707c/maintainability)](https://codeclimate.com/github/18F/identity-give-idemia/maintainability)
![Black](https://github.com/18F/identity-give-idemia/workflows/Black/badge.svg)
![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)

# GIVE Idemia In-Person-Proofing Microservice
The Idemia microservice is a Python Django application that uses the Django Rest
Framework to expose an API for in-person-proofing functions to GIVE.

## Why this project
The GIVE Idemia microservice aims to provide in-person proofing capabilites to
the GIVE API via its upstream Idema integration. The Idemia microservice has
the following goals:
* Provide locations for the in-person-proofing process
* Expose the Idemia pre-enrollment endpoints

## CI/CD Workflows with GitHub Actions
The most up-to-date information about the CI/CD flows for this repo can be found in the
[GitHub workflows directory](https://github.com/18F/identity-give-idemia/tree/main/.github/workflows)

## Building Locally

### Pre-requisites
Make sure you have the following installed if you intend to build the project locally.
- [Python 3](https://www.python.org/) (Check [runtime.txt](runtime.txt) for specific version)
- [CloudFoundry CLI](https://docs.cloudfoundry.org/cf-cli/)

### Development Setup
To set up your environment, run the following commands (or the equivalent
commands if not using a bash-like terminal):
```shell
# Clone the project
git clone https://github.com/18F/identity-give-idemia
cd identity-give-idemia

# Set up Python virtual environment
python3.9 -m venv .venv
source venv/bin/activate
# .venv\Scripts\Activate.ps1 on Windows

# Install dependencies and pre-commit hooks
python -m pip install -r requirements-dev.txt
pre-commit install
```

:warning: **If you are not able to install `psycopg2`**, please make sure you
have `libpq-dev` installed on your system. For apt, use the following
`sudo apt install -y libpq-dev`

### Required environment variables
The Django settings.py file for this project requires setting an environment
variable: `SECRET_KEY`

Running the following in your shell should print a secret key that can be used.
```shell
python
import secrets
secrets.token_urlsafe()
exit()

```

Set the environment variable using *the entire output* (including quotes) from
the printed secret
```shell
# BASH-like shells
export SECRET_KEY=<your-secret-here>
```
```powershell
# PowerShell
$Env:SECRET_KEY=<your-secret-here>
```
Note: during development, it may also be helpful to add the `DEBUG` environment
variable and setting it to the string `True`

Setup a local PSQL database to mirror the cloud.gov database used.
```
docker run -d --name dev-postgres -e POSTGRES_PASSWORD=postgres -v /tmp/idemia-microservice/:/var/lib/postgresql/data -p 5432:5432 postgres
```


### Running the application
After completing [development setup](#development-setup) and
[environment variable setup](#required-environment-variables) you can run the
application locally with:
```shell
python manage.py migrate
python manage.py collectstatic
python manage.py test --debug-mode
gunicorn -b 127.0.0.1:8080 idemia.wsgi
```

### Deploying to Cloud.gov during development
All deployments require having the correct Cloud.gov credentials in place. If
you haven't already, visit [Cloud.gov](https://cloud.gov) and set up your
account and CLI.

*manifest.yml* file contains the deployment configuration for cloud.gov, and expects
a vars.yaml file that includes runtime variables referenced. For info, see
[cloud foundry manifest files reference](https://docs.cloudfoundry.org/devguide/deploy-apps/manifest-attributes.html)

The application database must be deployed prior to the application, and can be
deployed with the following commands:
```shell
cf create-service aws-rds <plan> idemia-db
```

*You must wait* until the database has completed provisioning to continue with
the deployment. Wait for the `status` field of `cf service idemia-db` to
change from `create in progress` to `create succeeded`.
```shell
watch -n 15 cf service idemia-db
```

After the database has come up, running
`cf push --vars-file vars.yaml --var SECRET_KEY=$SECRET_KEY`.

### API Endpoints
#### /enrollment
Idemia pre-enrollment API functionality.

Direct requests to the microservice require the `X_CONSUMER_CUSTOM_ID` header to be set.

#### /locations
Exposes in-person proofing locations via the idemia UEP locations API

## Public domain

This project is in the worldwide [public domain](LICENSE.md). As stated in
[CONTRIBUTING](CONTRIBUTING.md):

> This project is in the public domain within the United States, and copyright
and related rights in the work worldwide are waived through the
[CC0 1.0 Universal public domain dedication](https://creativecommons.org/publicdomain/zero/1.0/).
>
> All contributions to this project will be released under the CC0 dedication.
By submitting a pull request, you are agreeing to comply with this waiver of
copyright interest.
