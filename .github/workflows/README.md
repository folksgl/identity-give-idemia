# GitHub Actions CI/CD workflows

## Black
The workflow outlined in `black.yml` checks to ensure that the Python style for this project is consistent and fully implemented in all Python files. For more information about this workflow, see https://black.readthedocs.io/en/stable/github_actions.html

## CodeQL-Analysis
The codeql-analysis workflow the CodeQL semantic code analysis engine to help find security issues very early on in the development process. See [CodeQL](https://securitylab.github.com/tools/codeql) for more details.

## Unit Tests
The unit-tests workflow will install the project runtime dependencies and run the unit test suite against the code.

## Deploy Dev
The deploy-dev workflow will only run if the unit-tests workflow completes successfully and deploys the project to the GIVE dev environment within Cloud.gov
