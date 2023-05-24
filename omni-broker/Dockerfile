FROM openjdk:8-jre-alpine
COPY target/*.jar /broker.jar
CMD java -jar /broker.jar $ENDPOINTS
