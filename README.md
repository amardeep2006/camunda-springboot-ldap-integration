# camunda-springboot-ldap-integration
Securing Camunda webapps and REST API via LDAP users

## Prerequisites
* Java 15 Onwards, Java 17 preferred
* A running instance of LDAP example OpenLDAP, Microsoft Active Directory, Oracle Internet Directory

### How to run locally

1. Install OpenLDAP and configure. I have provided screenshots for LDAP config in this page only.
2. Review the configuration in application.properties and make changes if required.
3. Run below command to start the project 

    ``./mvnw clean package``

    ``./mvnw spring-boot:run``

