# Celebdil
  > JAVA Backend repo

## Structure
Celebdil/src/main/java
* the starting path for tomcat/gradle
* /activity
  * the entry point from the frontend
* /data
  * objects to represent our data
* /service
  * the business logic
* /database
  * the entry point to the databases

  ### TODO
  - [X] Select java framework
    - Spring
  - [X] Select internal serving system
    - Gradle
  - [X] Select API service
    - RESTful API
  - [X] Figure out what the f*$k to do next...
    - Iterate through:
    *   activity
    *   service
    *   database
    - Add data as necessary
  - [ ] Get "Business objectives" for p0 (minimal viable product)
  - [ ] Release backend API support for p0 (v0)

## DEPENDENCIES
You will need the following packages (External Libraries):
Java                      : 1.8.0
Spring                    : 4.1.4
Spring-boot               : 2.0.1
Spring-boot-autoconfigure : 2.0.1
lombok                    : 1.16.20
Gradle                    : past 2.12 (??)

## BUILD ON WINDOWS
In Celebdil run:
* gradle.bat bootRun
  * runs server: currently on port 8080
* gradle.bat build
  * builds the jar file (so you can run program somewhere else)
potentially: ./gradlew for another operating system

## Currently supported APIs

* /user
* /home
