# --- Etapa de build ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cacheable: primero solo el pom para bajar dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# --- Etapa de runtime ---
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /workspace/target/core-api-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
