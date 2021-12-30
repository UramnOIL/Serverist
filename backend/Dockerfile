FROM openjdk AS build

WORKDIR /build
COPY .. .
RUN ["./gradlew", "backend:shadowJar"]

FROM openjdk:19-slim

WORKDIR /app

COPY --from=build /build/backend/libs/serverist-backend.jar .

RUN useradd -m -s /bin/bash serverist & chown serverist /app & chmod 500 serverist-backend.jar

CMD ["java -jar serverist-backend.jar"]