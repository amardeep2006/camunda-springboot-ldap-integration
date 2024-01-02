# camunda-springboot-ldap-integration
Securing Camunda webapps and REST API via LDAP users

## Prerequisites
* Java 17
* Docker
* Docker Compose

### How to run locally with OpenLDAP 

This will build and start both camunda containers and openldap. Also set en variables for openldap

`docker compose --profile openldap --profile buildcamunda up -d`


### How to run locally with Active Directory

This will build and start both camunda containers and AD . Also set en variables for Active directory

`docker compose --profile activedirectory up -d`

### Access camunda webapps

Open your favorite browser and open the below URL to test

http://localhost:8080

credentials are 

usename:camunda

Password: camunda123!

### Running application via IDE such as IntelliJIdea

I am using following plugin to start the openldap docker container

https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.docker-compose

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-docker-compose</artifactId>
    </dependency>
```

I have set default profile as openldap in application.yml. It will only start openldap container

```yml
spring:
  docker:
    compose:
      enabled: true # Set false for production environments where you have LDAP already running
      profiles:
        active: "openldap" # Set default profile
```