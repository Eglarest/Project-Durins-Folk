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
  - [ ] Select internal serving system
  - [X] Select API service
   - RESTful API
  - [ ] Figure out what the f*$k to do next...

** ON WINDOWS **
In Celebdil run:
* gradle.bat bootRun
  * runs server: currently on port 8080
* gradle.bat build
  * builds the jar file (so you can run program somewhere else)
potentially: ./gradlew for another operating system
