FROM openjdk:17-jdk-alpine

ENV APP_HOME /app
ENV APP_JAR bank-service-0.0.1-SNAPSHOT.jar

RUN mkdir $APP_HOME

COPY target/$APP_JAR $APP_HOME/bank-service.jar

WORKDIR $APP_HOME

ENTRYPOINT ["java", "-jar", "bank-service.jar"]