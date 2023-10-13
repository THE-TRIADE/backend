FROM maven:3.6.3-openjdk-17-slim as builder
COPY . /usr/src/app/
WORKDIR /usr/src/app/
## FIXME Num servidor, trocar para esse
# RUN bash mvnw clean package -Dmaven.test.skip

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim-buster
ENV TZ="America/Recife"
COPY --from=builder /usr/src/app/target/*jar /usr/src/app/app.jar
WORKDIR /usr/src/app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
