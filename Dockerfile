FROM maven:3.8.1-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

From openjdk:17-jdk-slim
COPY --from=build /target/app-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]