# Prox Module Service

The purpose of this service is to provide a backend for study courses and
modules, relevant for projects and topics inside Prox.

## Installation

After a `git clone` or download the project the following command must be executed once to initialize the projects.

Windows (CMD/PowerShell)

```posh
# Switch to project folder
cd .\prox-module-service\
# Execute initial build for git hooks...
.\mvnw.cmd clean test
```

Linux/MacOS (Bash/Terminal)

```bash
# Switch to project folder
cd prox-module-service/
# Execute initial build for git hooks...
./mvnw clean test
```

Executes the [Maven default lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) up to the `test` phase. During the `package` phase, an executable JAR and the Docker image are created.

## Local usage with docker

A Docker network named `prox` is required for the communication between services:

```bash
docker network create prox
```

Starts a Docker container based on the compose file and the image.

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

## Local usage in IntelliJ IDEA

For the necessary steps please look in [Run/Debug in IntelliJ IDEA](https://github.com/Archi-Lab/prox-local-setup#rundebug-in-intellij-idea).

## About the Team

This service is currently developed by members of the ArchiLab staff:

- Julian Lengelsen ([@jlengelsen](https://github.com/jlengelsen))
- Rudolf Grauberger ([@rudolfgrauberger](https://github.com/rudolfgrauberger))
