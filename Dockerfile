FROM openjdk:11

EXPOSE 8082

COPY target/*.jar /

ENTRYPOINT ["java", "-jar", "book-service-0.0.1-SNAPSHOT.jar"]