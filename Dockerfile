# Multi-stage build for Social Insurance Service (Health Insurance Only)

# ===== Build stage =====
FROM gradle:8.10.0-jdk21-alpine AS build

WORKDIR /app

# Copy Gradle wrapper and build files first (for better layer cache)
COPY build.gradle ./

# Generate Gradle wrapper first
RUN gradle wrapper --gradle-version 8.10.0

# Copy source code
COPY src src

# Build executable jar (skip tests; they are disabled anyway)
RUN ./gradlew bootJar --no-daemon

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose application port
EXPOSE 9003

# Environment variables to let you override DB connection from docker run
ENV SPRING_PROFILES_ACTIVE=default

ENTRYPOINT ["java","-jar","app.jar"]
