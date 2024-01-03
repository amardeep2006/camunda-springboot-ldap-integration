# camunda-springboot-ldap-integration
Securing Camunda webapps and REST API via LDAP users .

This is based on Camunda 7.20 and Springboot 3.1.x

## Prerequisites
* Java 17
* Docker
* Docker Compose

### How to run locally with OpenLDAP via Docker Compose

Clone the repo and running below command will build and start both camunda containers and openldap. Also set en variables for openldap

`docker compose --profile openldap --profile buildcamunda up -d`

### How to run locally with Active Directory

This will build and start both camunda containers and AD. Also set en variables for Active directory

`docker compose --profile activedirectory up -d`

### Access camunda webapps

Open your favorite browser and open the below URL to test

http://localhost:8080

credentials are 

usename:camunda

Password: camunda123!

### Running application via IDE such as IntelliJIdea

I am using following springboot plugin introduced in version 3.1 to start the openldap docker container

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

I am using springboot relaxed binding for overriding the configs from application.yml on runtime via springboot relaxed binding.

https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/#features.external-config

example :

ldap.server.uri: ldap://localhost:1389 is overriden by OS env variable 

LDAP_SERVER_URI: "ldap://openldap:1389"