# Multi-stage build for Social Insurance Service (Health Insurance Only)

# ===== Build stage =====
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven files first (for better layer cache)
COPY pom.xml ./

# Copy source code
COPY src src

# Build executable jar
RUN mvn clean package -DskipTests

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 9003

# Environment variables to let you override DB connection from docker run
ENV SPRING_PROFILES_ACTIVE=default

ENTRYPOINT ["java","-jar","app.jar"]
