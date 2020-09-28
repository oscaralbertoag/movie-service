# Movie Service (Sample Micro-Service)
Sample application for the "Increase Your Productivity By Mastering IntelliJ" course.
## Overview
This sample service is responsible for anything related to the "movie" domain in what would be a micro-service architecture.
It exposes a REST API for basic CRUD (Create, Read, Update, Delete) operations within its domain.
#### Key Domain Concepts
* Movie - a motion picture
* Contributor - anyone who participates and contributes to the creation of a movie (e.g. an actor, a director, a producer, etc)
#### Tools Used
* Spring Boot
* H2 In-Memory Database
* Gradle
* Java 11
* JUnit
## Build
To trigger a build, run the following command in a terminal from the root directory of this project:
```bash
# Linux/Mac
./gradlew clean build

# Windows
gradlew.bat clean build
```
## Run
To run the application, run the following command in a terminal from the root directory of this project:
```bash
# Linux/Mac
./gradlew bootRun

# Windows
gradlew.bat bootRun
```
To stop the application, use `CTRL` + `C`
## API Endpoints
A Postman collection is included in this project. To access it, navigate to the `tools` directory. 
This collection contains all the endpoints (and relevant request payloads) supported by this service.