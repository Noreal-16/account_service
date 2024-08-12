FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/account_movement_service-0.0.1-SNAPSHOT.jar movementservice.jar
COPY src/main/resources/schema.sql /app/schema.sql

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "movementservice.jar"]
