# camunda-springboot-ldap-integration
Securing Camunda webapps and REST API via LDAP users

## Prerequisites
* Java 17
* Docker
* Docker Compose

### How to run locally with OpenLDAP

`docker compose --profile runcontainers up -d`

This command will build both application and openldap containers.

Open your favorite browser and open the below URL to test

http://localhost:8080

credentials are camunda:camunda

### Running application via IDE such as IntelliJIdea

I am using following plugin to start the openldap docker container

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-docker-compose</artifactId>
    </dependency>
```