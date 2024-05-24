FROM openjdk:17-jdk-oracle
ARG JAR_FILE=target/employeemanager-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_employeemanager.jar
EXPOSE 8080
ENTRYPOINT {"java","-jar","app_employeemanager.jar"}