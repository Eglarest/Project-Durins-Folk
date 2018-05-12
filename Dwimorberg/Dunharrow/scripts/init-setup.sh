#!/bin/bash

os=${OSTYPE//[0-9.]/}

if [[ "$os" == "darwin" ]]; then
  # Script to start up an OSX postgreSQL database.
  printf '\nSetting up database for OSX\n';
  dbPath=$(PWD)/Dwimorberg/Dimholt;

  if [[ $(brew --version) ]]; then
    brew --version;
    brew update;

    if [[ $(postgres -V) ]]; then
      postgres -V;
      brew upgrade postgresql;
    else
      brew install postgresql;
    fi

    printf "\n--------------------  //  --------------------\nStopping and Cleaning out database\n--------------------\n";
    pg_ctl -D $dbPath stop;
    rm -r $dbPath/*;

    printf "\n--------------------\nCreating DB\n--------------------\n";
    initdb $dbPath -E utf8;

    printf "\n--------------------\nStarting DB\n--------------------  //  --------------------\n";
    pg_ctl -D $dbPath -l Dwimorberg/Dimholt/logfile start;
  else
    printf "\n--------------------  //  --------------------\nDid not find Homebrew on your system\nPlease install homebrew\n--------------------  //  --------------------\n";
  fi
elif [[ "$os" == "msys" ]]; then
  # Script to start up a Windows postgreSQL database.
  printf 'This is windows';
  # add the windows setup script here.
elif [[ "$os" == "linux" ]]; then
  # Script to start up a Linux postgreSQL database.
  printf "This is linux";
  # add the linux setup script here.
else
  # Script to let you know you need to add a script for your system.
  printf 'This is not a supported system';
fi
