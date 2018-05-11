#!/bin/bash

### Run this script to start up dev services

# Startup Dwimorberg Database
echo "\nStarting database setup.\n";
sh Argonath/scripts/init-setup.sh;
echo "\nDatabase setup complete!\n";
