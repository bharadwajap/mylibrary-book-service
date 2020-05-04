FROM openjdk:8-jdk-alpine
MAINTAINER GSD
LABEL description="mylibrary-book-service"
RUN ["mkdir", "-p", "/opt/app"]
WORKDIR /opt/app
COPY ["target/mylibrary-book-service*.jar", "mylibrary-book-service.jar"]
ENTRYPOINT ["java", "-Xmx256m", "-jar", "mylibrary-book-service.jar"]