FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY keycloak-spi/pom.xml .
COPY keycloak-spi/src ./src
RUN mvn clean package

FROM quay.io/keycloak/keycloak:25.0.0
COPY --from=build /app/target/keycloak-spi-1.0.jar /opt/keycloak/providers/

ENTRYPOINT ["/bin/kc.sh", "start-dev"]