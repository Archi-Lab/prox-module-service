# Prox Module Service

The purpose of this service is to provide a backend for study courses and
modules, relevant for projects and topics inside Prox.

## Installation

```bash
mvn clean install
```

Executes the
[Maven default lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
up to the install phase. During package phase a runnable JAR is created and
during install phase a docker image is build.

## Local usage

Powershell
```posh
$env:IMAGE='prox-module-service'; `
$env:TAG='latest'; `
docker-compose -f ./src/main/docker/docker-compose.yml up
```

Bash/Shell
```bash
export IMAGE="prox-module-service" &&
export TAG="latest" &&
docker-compose -f ./src/main/docker/docker-compose.yml up
```

Starts a Docker container based on the compose file and the image. A Docker
network named `prox` is required for the communication between services:

``` bash
docker network create prox
```

## About the Team

This service is currently developed by members of the ArchiLab staff:

- Julian Lengelsen ([@jlengelsen](https://github.com/jlengelsen))
- Rudolf Grauberger ([@rudolfgrauberger](https://github.com/rudolfgrauberger))