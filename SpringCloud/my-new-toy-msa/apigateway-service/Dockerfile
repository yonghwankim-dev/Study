FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY target/apigateway-1.0.jar Apigateway.jar  
ENTRYPOINT ["java", "-jar", "Apigateway.jar"]
