FROM maven:3.5-jdk-8-alpine AS build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package
FROM java:8
LABEL maintainer=“Ramandeep”
EXPOSE 8080
COPY --from=build /usr/src/app/target/KnapsackOptimizer-0.0.1-SNAPSHOT.jar /usr/app/KnapsackOptimizer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/app/KnapsackOptimizer-0.0.1-SNAPSHOT.jar"]