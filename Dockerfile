# Etapa de construcción
FROM gradle:8.9-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# Etapa de ejecución
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
