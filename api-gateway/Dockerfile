FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY api-gateway api-gateway
RUN chmod +x mvnw && ./mvnw -pl api-gateway -am clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/api-gateway/target/api-gateway-*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]
