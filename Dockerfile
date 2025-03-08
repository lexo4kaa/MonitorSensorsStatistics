FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /MonitorSensorsStatistics
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=build /MonitorSensorsStatistics/target/MonitorSensorsStatistics-1.0.jar sensors-statistics.jar
ENTRYPOINT ["java","-jar","/sensors-statistics.jar"]
EXPOSE 8080