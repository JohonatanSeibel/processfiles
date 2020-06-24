FROM openjdk:8-jdk-alpine
RUN mkdir -p /home/usr/data/in \
    && mkdir -p /home/usr/data/out
ARG JAR_FILE=target/*.jar
ENV HOMEPATH=/home/usr
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
