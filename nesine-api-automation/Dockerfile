# Multi-stage Dockerfile for nesine-api-automation
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven POM and pre-fetch dependencies for layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy project source code and resources
COPY src ./src

# Set English locale and encoding
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

# Default test command
CMD ["mvn", "clean", "test"]
