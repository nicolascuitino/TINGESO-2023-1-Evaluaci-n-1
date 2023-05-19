FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eval1-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/eval1-0.0.1-SNAPSHOT.jar"]
