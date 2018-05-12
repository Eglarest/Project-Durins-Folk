#!/bin/bash

### Run this script to start up dev services

# Startup Dwimorberg Database
printf "\nStarting database setup.\n";

sh Dwimorberg/Dunharrow/scripts/init-setup.sh;

cd Dwimorberg/Dunharrow/scripts/;
sh drop-sync-seed.sh;
cd ../../../;

printf "\nDatabase setup complete!\n";
