FROM adoptopenjdk:11-jdk-hotspot

COPY ./target/stockApp-0.0.1-SNAPSHOT.jar stockApp-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "stockApp-0.0.1-SNAPSHOT.jar"]