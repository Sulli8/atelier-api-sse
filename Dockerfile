FROM maven:eclipse-temurin

WORKDIR /app

COPY . .
RUN mvn clean package 

FROM eclipse-temurin:21-jre

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app/atelier-api-sullivan-sextius-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/atelier-api-sullivan-sextius-0.0.1-SNAPSHOT.jar"]