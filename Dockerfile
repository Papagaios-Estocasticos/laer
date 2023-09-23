FROM maven:3.9.4-amazoncorretto-17-debian AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:17-alpine3.17
COPY --from=build /home/app/target/laer-0.0.1.jar /usr/local/lib/laer.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/laer.jar"]