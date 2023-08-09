FROM adoptopenjdk:11-jdk-hotspot

COPY ./target/stockApp-0.0.1-SNAPSHOT.jar module2.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "module2.jar"]