## Beye-backend
[![CircleCI](https://circleci.com/gh/kunleawotunbo/beye-backend/tree/main.svg?style=shield&circle-token=108d36c8d2ec28bf03a5fb01d4985a4e40b63dde)](https://circleci.com/gh/kunleawotunbo/beye-backend/tree/circleci-project-setup)

This is a RESTful API for the business management application.

## Swagger
The Swagger UI page will then be available at http://localhost:8080/swagger-ui.html 

The OpenAPI description will be available at the following url for json format: http://localhost:8080/v3/api-docs

Features
--------

- **Authentication** 
    - Using Username/Email and Password
- **Account Management** 
    - Profile Details
    - Change Password
    - Forgot Password
    - Reset Password
    - Delete Account

### Build Instructions
#### Unit Tests
```bash
mvn clean test
```

#### Integration Tests
```bash
mvn clean integration-test
```

#### Run Server
```bash
mvn spring-boot:run
```
The application listens on port 8080 by default.

