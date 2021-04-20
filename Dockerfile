FROM openjdk:latest

ADD target/newyorktimes-article-search-engine-0.0.1-SNAPSHOT.jar cras-engine.jar

ENTRYPOINT ["java", "-jar", "cras-engine.jar"]

EXPOSE 8080