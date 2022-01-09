## Beye-backend
This is a RESTful API for the business management application.

## Swagger
The Swagger UI page will then be available at http://localhost:8080/swagger-ui.html 

The OpenAPI description will be available at the following url for json format: http://localhost:8080/v3/api-docs


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

