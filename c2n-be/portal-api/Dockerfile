FROM openjdk:8
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Shanghai
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
