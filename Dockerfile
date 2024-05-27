FROM openjdk:17-jdk-slim AS build

# Instalar pacotes necessários, incluindo Docker CLI
RUN apt-get update && apt-get install -y \
    findutils \
    curl \
    gnupg \
    apt-transport-https \
    ca-certificates \
    software-properties-common && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" && \
    apt-get update && \
    apt-get install -y docker-ce-cli

WORKDIR /home/app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew wrapper --gradle-version=7.5 --distribution-type=bin
#RUN ./gradlew clean build --no-daemon

FROM openjdk:17-jdk-slim

# Instalar Docker CLI na imagem final
RUN apt-get update && apt-get install -y \
    curl \
    gnupg \
    apt-transport-https \
    ca-certificates \
    software-properties-common && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" && \
    apt-get update && \
    apt-get install -y docker-ce-cli

WORKDIR /home/app

COPY --from=build /home/app /home/app

EXPOSE 8080

ENTRYPOINT ["./gradlew", "bootRun"]
