FROM eclipse-temurin:21-jdk-alpine AS spring-build

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package

FROM eclipse-temurin:21-jre-alpine AS spring-run
WORKDIR /app

# Marking for continous delivery and removal of dangling images
LABEL cicd="registration-spring-app"

COPY --from=spring-build /app/target/horizont-spring-app.jar /app/horizont-spring-app.jar
ENTRYPOINT ["java", "-jar", "horizont-spring-app.jar"]
