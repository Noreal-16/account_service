FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/account_movement_service-0.0.1-SNAPSHOT.jar  account_movement_service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "account_movement_service.jar"]
