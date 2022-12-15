FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/recentlyviewed-service-1.0.jar recentlyviewedservice.jar
ENTRYPOINT ["java","-jar","/recentlyviewedservice.jar"]
