FROM openjdk:13
WORKDIR /app
COPY target/*.jar /app/leadiro.jar
CMD java -XshowSettings:vm -jar leadiro.jar

