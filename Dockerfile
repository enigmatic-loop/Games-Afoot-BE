FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/games-afoot-0.0.1-SNAPSHOT.jar games-afoot.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/games-afoot.jar"]


#FROM openjdk:24-ea-jdk-slim
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]