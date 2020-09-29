# Government Identity Verification Engine

## In Person Proofing Pre-Enrollment Service

### Pre-requisites
- [Maven](https://maven.apache.org/) 
- [OpenJDK 8](https://developers.redhat.com/products/openjdk/download)
- [Postman](https://www.postman.com/downloads/)

### Getting started
Run `mvnw spring-boot:run` to build and run the application

This poject was created with [Spring Boot Initializr](https://start.spring.io/)

### Available Scripts

`mvnw clean install`

Builds the .jar files (including aws .jar file for lambda usage)

`mvnw spring-boot:run`

Runs the application.

The Rest API is hosted on `localhost:8080`.

### Available Endpoints

`GET /` - Only working endpoint

`GET /enrollment`

`GET /locations`

`POST /update`

`PUT /update`
