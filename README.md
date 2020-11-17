# Government Identity Verification Engine

## In Person Proofing Pre-Enrollment Service

### Pre-requisites
- [Maven](https://maven.apache.org/) 
- [OpenJDK 8](https://developers.redhat.com/products/openjdk/download)
- [AWS SAM](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
- [Docker](https://www.docker.com/products/docker-desktop)

### Build/Local Deploy

`sam build`

Builds the project.

`docker network create --driver=bridge sam_network`

Sets up a custom Docker network (intended for future local DynamoDB instances).

`sam local start-api --docker-network sam_network`

Runs the application.

The Rest API is hosted on `localhost:3000`.

### Available Endpoints

`GET /enrollment`
```
// Example Request Body
{
  "uuid": "c7b82090-172f-11eb-adc1-0242ac120002",
  "firstName": "Peter",
  "lastName": "Shumate",
  "emailAddress": "test@email.com"
}
```

`GET /location`

Required query parameter `zip` containing a valid ZIP code.

`GET /update`

Required query parameter `uuid` containing a valid UUID.

`PUT /update`

Required query parameter `ueid` containing a valid Idemia UEID.
```
// Example Request Body
{
  "ippstatus" : "IPP passed, identity successfully verified"
}
```
