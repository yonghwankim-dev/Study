FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY target/service-discovery-1.0.jar service-discovery.jar
ENTRYPOINT ["java", "-jar", "service-discovery.jar"]
