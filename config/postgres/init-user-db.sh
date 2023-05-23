#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER user01;
    ALTER USER user01 with password 'passw0rd';

    CREATE DATABASE kc_db_local;
    GRANT ALL PRIVILEGES ON DATABASE kc_db_local TO user01;

    \connect kc_db_local;
    CREATE SCHEMA IF NOT EXISTS kc_schema_local_0 AUTHORIZATION user01;
EOSQL
