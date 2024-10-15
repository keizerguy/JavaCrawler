# Use a Debian-based OpenJDK image that supports apt-get
FROM openjdk:23-slim-bullseye AS builder

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy project files
COPY pom.xml /app/
COPY index.html /app/
COPY src /app/src

# Run Maven to build the project
RUN mvn -f /app/pom.xml clean package
RUN ls /app/target

# Use a slimmer OpenJDK image for the runtime stage
FROM openjdk:23-slim-bullseye

# Copy the JAR from the builder stage
COPY --from=builder /app/target/JavaCrawler-1.0-SNAPSHOT.jar /app/main.jar

COPY --from=builder /app/index.html /index.html

# Expose ports
EXPOSE 8080

# Run the application
#ENTRYPOINT ["java", "--add-opens", "java.management/sun.management=ALL-UNNAMED", "-jar", "/app/main.jar"]
# Run the application with a delay
ENTRYPOINT ["sh", "-c", "sleep 40 && java --add-opens java.management/sun.management=ALL-UNNAMED -jar /app/main.jar"]