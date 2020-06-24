FROM openjdk:8-jdk-alpine
RUN mkdir -p /root/data/in \
    && mkdir -p /root/data/out
ARG JAR_FILE=target/*.jar
ENV HOMEPATH=/root
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
