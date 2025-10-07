# Etapa 1: build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render expone el puerto que le pasa como variable de entorno
ENV PORT=8080
EXPOSE 8080

# Le decimos a Spring Boot que use ese puerto
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","app.jar"]
