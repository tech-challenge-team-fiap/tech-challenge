FROM openjdk:17-jdk-slim-buster

ADD api/target/tech-challenge-app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]