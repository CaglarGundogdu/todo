FROM openjdk:8-jdk-alpine

ARG JAR_FILE=release/todo-server-0.0.1.jar

COPY ${JAR_FILE} todo-server-0.0.1.jar

ENTRYPOINT ["java","-jar","/todo-server-0.0.1.jar"]