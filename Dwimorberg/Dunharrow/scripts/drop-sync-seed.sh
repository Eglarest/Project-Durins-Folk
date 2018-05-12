#!/usr/bin/env bash
echo WARNING: YOU WILL LOSE ALL OF YOUR TABLES AND DATA IN YOUR LOCAL TESTING DATABASE!

# kick off any existing users
psql -d postgres -c "UPDATE pg_database SET datallowconn = 'false' WHERE datname = 'dwimorberg'";
psql -d postgres -c "ALTER DATABASE dwimorberg CONNECTION LIMIT 1";
psql -d postgres -c "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'dwimorberg'";

# drop then create the testing user and DB
psql -d postgres -c "DROP DATABASE dwimorberg";
psql -d postgres -c "DROP USER IF EXISTS writeruser";
psql -d postgres -c "CREATE DATABASE dwimorberg";
psql -d postgres -c "CREATE USER writeruser WITH PASSWORD 'password'";

# sync the current data models
node create-tables.js local;

# grant all rights to the testing user
psql -d dwimorberg -c "GRANT ALL PRIVILEGES ON DATABASE dwimorberg TO writeruser";
psql -d dwimorberg -c "GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO writeruser";
psql -d dwimorberg -c "GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO writeruser";
psql -d dwimorberg -c "CREATE EXTENSION IF NOT EXISTS citext WITH SCHEMA public;";


# seed static data from "seeders" folder
cd .. && sequelize db:migrate --env local && sequelize db:seed:all --env local && cd scripts
