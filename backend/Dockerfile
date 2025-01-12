FROM openjdk:21-jdk-slim
ARG JAR_FILE=build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
COPY ${JAR_FILE} backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]