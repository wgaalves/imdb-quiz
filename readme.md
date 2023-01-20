
# [Imdb Quiz with oauth2 authentication](https://github.com/wgaalves/imdb-quiz/)

## What you'll build
- A simple spring cloud project with oath2 authentication

## What you'll need
- Docker CE

## Stack
- Docker
- Docker-compose
- Java 11/77
- Spring Boot
- Keycloak
- Gradle

## Befor Run
- Run command `docker build ./gateway/ -t quiz/gateway -f gateway/Dockerfile`
- Run command `docker build ./quiz-service/ -t quiz/quiz-service -f quiz-service/Dockerfile`

## Run
- Run command `docker-compose up -d`

## Script After Run

-

## Documentation
- Swagger on `http://localhost:8060/quiz/swagger-ui/index.html`


## Sequence Diagram

```mermaid
sequenceDiagram
User->>Gateway: /login
Gateway->>Keycloak: /authenticate
Keycloak->>Gateway: jwt
Gateway->>User: jwt
User->>Gateway: /quiz/**
Gateway->>Quiz-Service: /quiz/**
Quiz-Service->>Keycloak: verify access_key
Keycloak->>Quiz-Service: response
Quiz-Service->>Gateway: resource response
Gateway->>User: resource response
```