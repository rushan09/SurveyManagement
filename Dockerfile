FROM eclipse-temurin:21-jre

WORKDIR /opt/app
COPY target/surveyapp.jar app.jar
COPY target/dependency/*.jar dependency/

EXPOSE 8080

CMD ["java", "-cp", "app.jar:dependency/*", "org.survey.example.App", "server"]
