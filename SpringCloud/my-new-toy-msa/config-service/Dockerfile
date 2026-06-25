FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY target/config-service-1.0.jar config-service.jar  
ENTRYPOINT ["java", "-jar", "config-service.jar"]
