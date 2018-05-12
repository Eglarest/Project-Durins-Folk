#!/bin/bash

os=${OSTYPE//[0-9.]/}

if [[ "$os" == "darwin" ]]; then
  # Script to stop an OSX postgreSQL database.
  dbPath=$(PWD)/Dwimorberg/Dimholt;

  printf "\n--------------------  //  -------------------- \nStopping database at " $dbPath " for OSX\n--------------------\n";

  pg_ctl -D $dbPath stop;

  printf "\n--------------------\nDatabase Stopped\n--------------------  //  -------------------- \n";

elif [[ "$os" == "msys" ]]; then
  # Script to stop an Windows postgreSQL database.
  printf 'This is windows';
  # add the windows setup script here.
elif [[ "$os" == "linux" ]]; then
  # Script to stop an Linux postgreSQL database.
  printf "This is linux";
  # add the linux setup script here.
else
  # Script to let you know you need to add a script for your system.
  printf 'This is not a supported system';
fi
