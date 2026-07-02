FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY target/order-service-1.0.jar order-service.jar
ENTRYPOINT ["java", "-jar", "order-service.jar"]
