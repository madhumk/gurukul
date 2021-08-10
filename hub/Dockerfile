FROM maven:3.8.1-openjdk-11-slim AS BUILD
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:11-jdk-slim AS RUNTIME
WORKDIR /app/
COPY --from=BUILD /tmp/target/hub-0.0.1-SNAPSHOT.jar .
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "hub-0.0.1-SNAPSHOT.jar"]