FROM eclipse-temurin:17-jdk-jammy as build-stage
WORKDIR /app
COPY . .
RUN ./mvnw clean package

FROM eclipse-temurin:17-jre-jammy
COPY --from=build-stage /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
