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
      - [ ] /login
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
    - [ ] MessageService
    - [ ] UserGroupService
    - [ ] UserService
    - [ ] ValidationService
  - [ ] Finish database logic
    - [ ] ActivitiesDatabase
    - [ ] ContactsDatabase
    - [ ] EventsDatabase
    - [ ] LoginDatabase (Temporary)
    - [ ] MessagesDatabase
    - [ ] UserGroupsDatabase
    - [ ] UsersDatabase
  - [ ] Finishing Touches
    - [ ] Check Controller DateTime Timezone Logic
    - [ ] Remove Redundant nullPointer checks
    - [ ] Finalize Exception Logic

## DEPENDENCIES
You will need the following packages (External Libraries):
Java                      : 1.8.0
Spring                    : 4.1.4
Spring-boot               : 2.0.1
Spring-boot-autoconfigure : 2.0.1
lombok                    : 1.16.20
Gradle                    : past 2.12 (??)
Guava                     : 24.1-jre

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

### Mostly Stable

### Turbulent

* /home
* /login

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
* /send-message
* /get-messages-to
* /get-messages-from
* /get-messages-between

## Code Styles (unless you know better ones...)

Best Practices:
* no lines over 140 characters
* no .* imports
* use String.format(...) if more than 1 variable in String expression
* use SOLID principles (When you can)
* don't repeat code; if you're duplicating, pull it out into it's own method

Suggestions:
* 1 space between ) and {
* never more than 1 blank line
* no blank lines at the end of a method/class

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

## Git help

### Git Workflow: (to help minimize growing pains with git)

1) Begin on local/master branch
2) git pull
3) git checkout -b newBranchName
4) make changes and test changes
5) git add <all changes> (git add Celebdil/src/* often works)
6) git commit -a -m "Description of work"
7) git rebase -i master **ONLY IF MORE THAN 1 COMMIT IN BRANCH**
* this should open a text editor
* change all commits EXCEPT the first from "pick" to "squash"
* save and exit ('Esc' then :wq in vim)
* this should bring up a second text editor
* delete all commit messages except one, leaving the one describing what your changes are
* save and exit
8) git checkout master
9) git pull
10) git checkout newBranchName
11) git rebase master
* you may need to resolve some git conflicts:
  * git status will tell you the files
  * edit the files to choose the changes you want
  * git rebase --continue
12) git checkout master
13) git pull
* if this says 'Already up to date.' continue.
* if not return to step 10.
14) git merge newBranchName
15) git push
16) You can chose to clean up your local git if you wish
* git branch -d newBranchName **This will delete your old working branch**

### If you messed up

git reset HEAD^ - will undo 1 commit

If you made changes directly to your master branch:
* git stash save
* git checkout -b newBranchName
* git stash apply