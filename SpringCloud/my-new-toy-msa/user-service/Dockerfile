FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY target/user-service-1.0.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]
