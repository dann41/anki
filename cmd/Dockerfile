FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/cmd-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]