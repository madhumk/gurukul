FROM maven:3.8.1-openjdk-11-slim AS BUILD
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:11-jdk-slim AS RUNTIME
RUN ln -s /lib64/ld-linux-x86-64.so.2 /lib/ld-linux-x86-64.so.2
WORKDIR /app/
COPY --from=BUILD /tmp/target/streams-1.0-SNAPSHOT-jar-with-dependencies.jar .
ENTRYPOINT ["java", "-cp", "streams-1.0-SNAPSHOT-jar-with-dependencies.jar", "com.gurukul.streams.TestStreams"]
