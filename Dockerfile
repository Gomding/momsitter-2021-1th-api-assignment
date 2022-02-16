FROM openjdk:11
COPY account-api/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
