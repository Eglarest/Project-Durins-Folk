#!/bin/bash

os=${OSTYPE//[0-9.]/}

if [[ "$os" == "darwin" ]]; then
  # Script to start up an OSX dev system.
  echo 'Setting up dev system for OSX';
  if [[ $(brew --version) ]]; then
    echo "Found" $(brew --version);
    brew update;
    if [[ $(postgres -V) ]]; then
      echo "Found" $(postgres -V);
      brew upgrade postgresql;

      dbPath=$(PWD)/Dwimorberg/Dimholt;

      echo "\n --------------------  //  -------------------- \nStopping and Cleaning out database at" $dbPath "\n --------------------  //  -------------------- \n"
      pg_ctl -D $dbPath -l logfile stop;
      rm -r $dbPath/*;

      echo "\n --------------------  //  -------------------- \nCreating DB\n --------------------  //  -------------------- \n"
      initdb $dbPath -E utf8;

      echo "\n --------------------  //  -------------------- \nStarting DB\n --------------------  //  -------------------- \n"
      pg_ctl -D $dbPath -l logfile start;

      cd Argonath
      npm run db:setup;
      cd ..

      echo "YAY"

      # pg_ctl -D $dbPath -l logfile stop;
    else
      brew install postgresql;
    fi
  else
    echo "\n --------------------  //  -------------------- \nDid not find Homebrew on your system\nPlease install homebrew\n --------------------  //  -------------------- \n";
  fi
elif [[ "$os" == "msys" ]]; then
  # Script to start up a Windows dev system.
  echo 'This is windows';
  # add the windows setup script here.
elif [[ "$os" == "linux" ]]; then
  # Script to start up a Linux dev system.
  echo "This is linux";
  # add the linux setup script here.
else
  # Script to let you know you need to add a script for your system.
  echo 'This is not a supported system';
fi
