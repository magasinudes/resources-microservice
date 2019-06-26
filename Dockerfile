FROM openjdk:8-jdk-slim as runtime

WORKDIR /srv
COPY    . .

ENTRYPOINT ["java"]
CMD ["-jar", "build/libs/resources-microservice-0.1.1.jar"]