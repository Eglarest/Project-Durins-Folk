# Celebdil
  > JAVA Backend repo

## Structure
Celebdil/src/main/java
* the starting path for tomcat/gradle
* /controller
  * the entry point from the frontend
  * transform to interior model and validate
* /data
  * objects to represent our data
* /service
  * the business logic
  * should not "catch" exceptions (handle those at the endpoints)
* /database
  * the entry point to the databases
  * transform from interior model and validate/retry
* /exception
  * the exceptions specific to this service

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
    -  We need the following APIs working for p0 (working list):
      - [X] /login
        - log in a created user
      - [ ] /get-user
        - get and return a user their correct data
      - [ ] /get-user-group
        - get a groups details
      - [X] /home
        - provide a personalized home message to a user
      - [ ] /user-events
        - provide a user's events for at least 1 day
      - [ ] /group-events
        - provide a group's events for at least 1 day
      - [ ] /join-event
        - allow a user to join a created event
      - [ ] /create-event
        - allow a user to create an event
      - [ ] /add-user-to-group
        - allow a user to join a created group
      - [X] /send-message
        - allow a user to send a message
      - [X] /get-messages-to
        - allow a user to get the messages sent to them
      - [X] /get-messages-from
        - allow a user to get the messages they have sent
      - [X] /get-messages-between
        - allow a user to get the messages between 2 users
  - [ ] Release backend API support for p0 (v0)
  - [X] Finish controller logic
    - [X] EventController
    - [X] HomepageController
    - [X] LoginController
    - [X] MessageController
    - [X] UserController
    - [X] UserGroupController
  - [ ] Finish service logic
    - [ ] ActivityService
    - [ ] AddressService
    - [ ] CredentialService
    - [ ] EventService
    - [X] MessageService
    - [ ] UserGroupService
    - [ ] UserService
    - [ ] ValidationService
  - [ ] Finish database logic
    - [ ] ActivitiesDatabase
    - [ ] ContactsDatabase
    - [ ] EventsDatabase
    - [ ] LoginDatabase (Temporary)
    - [X] MessagesDatabase
    - [ ] UserGroupsDatabase
    - [ ] UsersDatabase
    - [ ] PostgreSQLJDBC
  - [ ] Finishing Touches
    - [ ] Check Controller DateTime Timezone Logic
    - [ ] Remove Redundant nullPointer checks
    - [ ] Finalize Exception Logic
    - [ ] Add size restrictions to database input at database level

## DEPENDENCIES
You will need the following packages (External Libraries):
Java                      : 1.8.0
Spring                    : 4.1.4
Spring-boot               : 2.0.1
Spring-boot-autoconfigure : 2.0.1
lombok                    : 1.16.20
Gradle                    : 4.7
Guava                     : 24.1-jre
PostgreSQL-JDBC           : 42.2.2.2
JSON                      : 20090211

## Database Creation:
You will need to install the following database code:
PostgreSQL - 10
* https://www.postgresql.org/download/
The JDBC from PostgreSQL - 4.2
* https://jdbc.postgresql.org/download.html

Next create the databases:

This will create 2 databases for you "durinsfolktest" and "durinsfolk". Which will be useful in testing locally, but will need to be populated with data.
The select statements are to make sure the tables are properly formatted.

From the command line run:
* `psql -U postgres`
* `create database durinsfolktest;`
* `\c durinsfolktest;`
* `create table Users (account_id uuid UNIQUE NOT NULL, title text, first_name text NOT NULL, middle_name text, surname text NOT NULL, suffix text, address json, join_date timestamp NOT NULL);`
* `create table Events (event_id uuid UNIQUE NOT NULL, name text NOT NULL, date timestamp NOT NULL, length bigint, address json, parent uuid, attendees uuid[], activities uuid[]);`
* `create table Activities (activity_id uuid UNIQUE NOT NULL, name text NOT NULL, type text NOT NULL);`
* `create table Contacts (lower_account_id uuid NOT NULL, higher_account_id uuid NOT NULL, first_contact timestamp, last_contact timestamp, shared_events uuid[], CHECK (lower_account_id < higher_account_id), UNIQUE (lower_account_id, higher_account_id));`
* `create table Messages (message_id uuid UNIQUE NOT NULL, content text, date timestamp NOT NULL, to_user uuid NOT NULL, from_user uuid NOT NULL);`
* `create table UserGroups (group_id uuid UNIQUE NOT NULL, name text NOT NULL, creation_date timestamp NOT NULL, address json, owners uuid[] NOT NULL, members uuid[]);`
* `create table Login (account_id uuid UNIQUE NOT NULL, username text UNIQUE NOT NULL, password text NOT NULL);`
* `insert into messages values ('00000000-0000-0000-0000-000000000000', "Welcome to Project Durins Folk! Sit back and have an ale while you wait.", '2018-04-25 8:30:00pm', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');`
* `select * from Users limit 1;`
* `select * from Events limit 1;`
* `select * from Activities limit 1;`
* `select * from Contacts limit 1;`
* `select * from Messages limit 1;`
* `select * from UserGroups limit 1;`
* `select * from Login limit 1`

From the command line run:
* `psql -U postgres`
* `create database durinsfolk;`
* `\c durinsfolk;`
* same commands as above

## BUILD ON WINDOWS
Add the gradle.bat file's directory to your $PATH environment variable
* you will need to close and reopen your terminal after this
In Celebdil run:
* gradle bootRun --stacktrace
  * runs server: currently on port 8080
  * just 'Ctrl-C' to stop the server
  * if it takes over about 10 seconds without output the first time:
    * 'Ctrl-C'
    * Restart
* gradle build --stacktrace
  * builds the jar file (so you can run program somewhere else)

potentially: ./gradlew for another operating system

## Currently "supported" APIs

### Stable

* /login
* /home

### Mostly Stable

* /send-message
* /get-messages-to
* /get-messages-from
* /get-messages-between

### Turbulent

### Highly Turbulent

* /user-events
* /group-events
* /join-event
* /create-event
* /create-user
* /get-user
* /find-users
* /get-user-group
* /find-user-groups
* /add-user-to-group
* /create-user-group

## Setting up IntelliJ:
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
  * Add guava 24.1-jre jar file
* Libraries // This may not be necessary
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

## Suggested Supplemental Software

IDE:
* IntelliJ
  * Smart IDE with built in Dark mode and auto import
Test Software:
* Postman
  * Software to help make GET and POST requests to a service
  * Can also save a list of requests to run, which can generate a nice manual test suite while we wait on unit tests
Music:
* Spotify
  * https://open.spotify.com/user/keegansymmes/playlist/4WebbTLBrqL7QNfZJXnJUK?si=-v55pIf5Q3e1EurXFY2JBA
    * Add something worth listening to (preference for hobbit and dwarf music)

## Database Creation:
You will need to install the following database code:
PostgreSQL - 10
* https://www.postgresql.org/download/
The JDBC from PostgreSQL - 4.2
* https://jdbc.postgresql.org/download.html

Next create the databases:

This will create 2 databases for you "durinsfolktest" and "durinsfolk". Which will be useful in testing locally, but will need to be populated with data.
The select statements are to make sure the tables are properly formatted.

From the command line run:
* `psql -U postgres`
* `create database durinsfolktest;`
* `\c durinsfolktest;`
* `create table Users (account_id uuid, title text, first_name text, middle_name text, surname text, suffix text, address json, join_date timestamp);`
* `create table Events (event_id uuid, name text, date timestamp, length interval, address json, parent uuid, attendees uuid[], activities uuid[]);`
* `create table Activities (activity_id uuid, name text, type text);`
* `create table Contacts (lower_account_id uuid, higher_account_id uuid, first_contact timestamp, last_contact timestamp, shared_events uuid[]);`
* `create table Messages (message_id uuid, content text, date timestamp, to_user uuid, from_user uuid);`
* `create table UserGroups (group_id uuid, name text, creation_date timestamp, address json, owners uuid[], members uuid[]);`
* `create table Login (account_id uuid, username text, password text);`
* `select * from Users limit 1;`
* `select * from Events limit 1;`
* `select * from Activities limit 1;`
* `select * from Contacts limit 1;`
* `select * from Messages limit 1;`
* `select * from UserGroups limit 1;`
* `select * from Login limit 1`

From the command line run:
* `psql -U postgres`
* `create database durinsfolk;`
* `\c durinsfolk;`
* `create table Users (account_id uuid UNIQUE NOT NULL, title text, first_name text NOT NULL, middle_name text, surname text NOT NULL, suffix text, address json, join_date timestamp NOT NULL);`
* `create table Events (event_id uuid UNIQUE NOT NULL, name text NOT NULL, date timestamp NOT NULL, length interval NOT NULL, address json, parent uuid, attendees uuid[], activities uuid[]);`
* `create table Activities (activity_id uuid UNIQUE NOT NULL, name text NOT NULL, type text NOT NULL);`
* `create table Contacts (lower_account_id uuid NOT NULL, higher_account_id uuid NOT NULL, first_contact timestamp, last_contact timestamp, shared_events uuid[], CHECK (lower_account_id < higher_account_id), UNIQUE (lower_account_id, higher_account_id));`
* `create table Messages (message_id uuid UNIQUE NOT NULL, content text, date timestamp NOT NULL, to_user uuid NOT NULL, from_user uuid NOT NULL);`
* `create table UserGroups (group_id uuid UNIQUE NOT NULL, name text NOT NULL, creation_date timestamp NOT NULL, address json, owners uuid[] NOT NULL, members uuid[]);`
* `create table Login (account_id uuid UNIQUE NOT NULL, username text UNIQUE NOT NULL, password text NOT NULL);`
* `select * from Users limit 1;`
* `select * from Events limit 1;`
* `select * from Activities limit 1;`
* `select * from Contacts limit 1;`
* `select * from Messages limit 1;`
* `select * from UserGroups limit 1;`
* `select * from Login limit 1`
