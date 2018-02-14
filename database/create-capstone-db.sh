#!/bin/bash
BASEDIR=$(dirname $0)
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres capstone &&
psql -U postgres -d capstone -f "$BASEDIR/schema.sql" &&
psql -U postgres -d capstone -f "$BASEDIR/user.sql" &&
psql -U postgres -d capstone -f "$BASEDIR/data.sql"