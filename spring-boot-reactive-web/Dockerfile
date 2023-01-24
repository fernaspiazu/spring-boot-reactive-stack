FROM openjdk:17 AS builder
COPY . /usr/src/reactive-backend
WORKDIR /usr/src/reactive-backend
RUN ./mvnw clean package

FROM openjdk:17
COPY --from=builder /usr/src/reactive-backend/target/spring-boot-reactive-web-0.1.0-SNAPSHOT.jar /usr/src/reactive-backend/
WORKDIR /usr/src/reactive-backend
EXPOSE 8080
CMD ["java", "-jar", "spring-boot-reactive-web-0.1.0-SNAPSHOT.jar"]