#!/usr/bin/env bash
echo WARNING: YOU WILL LOSE ALL OF YOUR TABLES AND DATA IN YOUR LOCAL TESTING DATABASE!

# kick off any existing users
psql -d template1 -c "UPDATE pg_database SET datallowconn = 'false' WHERE datname = 'testing'";
psql -d template1 -c "ALTER DATABASE testing CONNECTION LIMIT 1";
psql -d template1 -c "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'testing'";

# drop then create the testing user and DB
psql -d template1 -c "DROP DATABASE testing";
psql -d template1 -c "DROP USER IF EXISTS writeruser";
psql -d template1 -c "CREATE DATABASE testing";
psql -d template1 -c "CREATE USER writeruser WITH PASSWORD 'password'";

# sync the current data models
node create-tables.js local;

# grant all rights to the testing user
psql -d testing -c "GRANT ALL PRIVILEGES ON DATABASE testing TO writeruser";
psql -d testing -c "GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO writeruser";
psql -d testing -c "GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO writeruser";
psql -d testing -c "CREATE EXTENSION IF NOT EXISTS citext WITH SCHEMA public;";


# seed static data from "seeders" folder
cd .. && sequelize db:migrate --env local && sequelize db:seed:all --env local && cd scripts
