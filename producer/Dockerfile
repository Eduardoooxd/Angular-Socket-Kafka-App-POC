# Install Maven with JDK 11 to build jar

FROM maven:3.9.0-amazoncorretto-11 AS build

WORKDIR /app
COPY src /src
COPY pom.xml .

# build
RUN mvn clean package

EXPOSE 8080
CMD ["java", "-jar", "/app/target/quarkus-app/quarkus-run.jar" ,"-Dquarkus.http.host=0.0.0.0"]
