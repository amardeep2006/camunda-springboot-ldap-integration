#Use latest ubuntu base build of 22.04
FROM ubuntu:22.04 as builder
#Install Required OS Packages
RUN apt-get update -yq \
     && apt-get install  bash -yq \
     && apt-get install  apt-utils -yq \
     && apt-get install  ca-certificates -yq \
     && apt-get install  curl -yq \
     && apt-get install  tzdata -yq \
     && apt-get install  tini -yq \
     && apt-get install -y curl gnupg openjdk-17-jdk -y
RUN  addgroup -gid 1000 camunda \
     && adduser --uid 1000 --gid 1000 --shell /bin/bash --home /camunda camunda \
     && chmod 755 /camunda \
     && chown camunda:camunda /camunda

# Build the Spring Boot project
WORKDIR /camunda
COPY . /camunda
RUN cd /camunda \
    && ls -ltra \
    && ./mvnw clean package

# Main Docker image building
FROM ubuntu:22.04

ENV DB_DRIVER=
ENV DB_URL=
ENV DB_USERNAME=
ENV DB_PASSWORD=
ENV TZ=UTC
ENV DEBUG=false
ENV JAVA_OPTS=""

#Install Required OS Packages
RUN apt-get update -yq \
     && apt-get install  bash -yq \
     && apt-get install  apt-utils -yq \
     && apt-get install  ca-certificates -yq \
     && apt-get install  curl -yq \
     && apt-get install  tzdata -yq \
     && apt-get install  tini -yq \
     && apt-get install -y curl gnupg openjdk-17-jdk -y
RUN  addgroup -gid 1000 camunda \
     && adduser --uid 1000 --gid 1000 --shell /bin/bash --home /camunda camunda \
     && chmod 755 /camunda \
     && chown camunda:camunda /camunda
WORKDIR /camunda
COPY camunda.sh /camunda
RUN chmod +x /camunda/camunda.sh
USER camunda
COPY --chown=camunda:camunda --from=builder /camunda/target/camunda-ldap-project-core.jar .
EXPOSE 8080
ENTRYPOINT ["/usr/bin/tini", "--"]
CMD ["./camunda.sh"]