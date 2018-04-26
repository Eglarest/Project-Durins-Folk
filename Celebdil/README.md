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

### Setting up IntelliJ:
Project is Celebdil
Right click on Celebdil/src
* Mark Directory as -> Sources Root

File -> Project Structure
* Project
  * Project SDK = Java 1.8
* Modules
  * Add Spring Folder
  * Add folder containing:
    * Spring-boot and Spring-boot-autoconfigure
  * Add lombok 1.16.20 jar file
* Libraries
  * Add lombok - jar file

File -> Settings
* Plugins
  * Search for and add
    * RESTfulToolkit
    * Lombok Plugin
* Build, Execution, Deployment -> Compiler -> Annotation Processors
  * Check "Enable Annotation Processing"
* Editor -> General -> Auto Import
  * Check all boxes
* Editor -> Code Style -> Java
  * Imports
    * Set "Class Count to use import with '*'" to 999
    * Set "Names Count to use static import with '*'" to 999
* Editor -> Color Scheme
  * Set to 'Darcula' so you don't go blind

Spotify: https://open.spotify.com/user/keegansymmes/playlist/4WebbTLBrqL7QNfZJXnJUK?si=-v55pIf5Q3e1EurXFY2JBA
* Add something worth listening to (preferences for hobbit and dwarf music)