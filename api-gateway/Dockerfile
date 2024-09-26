FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY . .

RUN gradle clean build --no-daemon

COPY --from=builder /app/build/libs/*.jar /app/app.jar


ENTRYPOINT ["java", "-jar", "/app/app.jar"]
