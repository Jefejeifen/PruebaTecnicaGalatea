# ===========================
# Etapa de construcción
# ===========================
FROM gradle:8.9-jdk17-alpine AS build

# Definimos el directorio de trabajo
WORKDIR /app

# Copiamos todos los archivos del proyecto
COPY . .

# Damos permisos de ejecución al Gradle Wrapper
RUN chmod +x gradlew

# Ejecutamos el build sin pruebas para optimizar tiempo
RUN ./gradlew clean build -x test

# ===========================
# Etapa de ejecución
# ===========================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el artefacto (JAR) generado desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exponemos el puerto 8080 (por defecto en Spring Boot)
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
