## Base image
#FROM eclipse-temurin:17-jdk-alpine
#
## Set working directory
#WORKDIR /app
#
## Copy built JAR
#COPY target/PatientService-0.0.1-SNAPSHOT.jar app.jar
#
## Expose service port
#EXPOSE 8081
#
## Run the application
#ENTRYPOINT ["java","-jar","app.jar"]

#FROM eclipse-temurin:17-jdk
#ARG JAR_FILE=target/PatientService-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


FROM eclipse-temurin:17-jdk
WORKDIR /app
ARG JAR_FILE=target/PatientService-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]


