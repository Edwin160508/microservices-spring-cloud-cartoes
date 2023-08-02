FROM maven:3.8.5-openjdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG EUREKA_SERVER=localhost
ARG EUREKA_USER=user
ARG EUREKA_PASSWORD=password
ARG EUREKA_PORT=port
ARG KEYCLOAK_SERVER=localhost
ARG KEYCLOAK_PORT=port

ENTRYPOINT java -jar app.jar