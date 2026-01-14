# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the JAR
RUN mvn -q -DskipTests package


# ---- Run Stage ----
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]