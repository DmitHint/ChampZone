FROM gradle:8.7.0-jdk21-alpine AS builder
WORKDIR /app

COPY . /app/.

RUN gradle clean build --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
