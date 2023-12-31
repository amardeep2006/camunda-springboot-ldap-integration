FROM bitnami/openldap:2.6.6
USER root
COPY ldifs /ldifs
COPY schemas /schemas
# Update os dependencies
RUN apt-get update && apt-get dist-upgrade -y
USER 1001
