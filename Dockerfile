FROM java:8
LABEL maintainer=“Ramandeep”
EXPOSE 8080
FROM maven:3.5-jdk-8-alpine
WORKDIR /code
ADD . /code
RUN ["mvn", "package"]
ADD target/KnapsackOptimizer-0.0.1-SNAPSHOT.jar KnapsackOptimizer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","KnapsackOptimizer-0.0.1-SNAPSHOT.jar"]