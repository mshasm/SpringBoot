# Stage 1: Build the application using Maven and OpenJDK 19 (Eclipse Temurin)
FROM maven:3.8.7-eclipse-temurin-19 as build

# Set the working directory in the container
WORKDIR /app

# Copy the entire project into the container
COPY ./ /app

RUN rm -rf target/

#RUN mvn clean install
RUN mvn clean install -DskipTests

# Build the Spring Boot application (assuming your Maven project is set up correctly)
RUN ls -la
# Stage 2: Run the application using OpenJDK 19 runtime
FROM eclipse-temurin:19-jre

# Set the working directory in the container for the runtime stage
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 for the Spring Boot application
EXPOSE 8080

# Run the Spring Boot application
CMD ["sh", "-c", "sleep 10 && java -jar app.jar"]


