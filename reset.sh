#!/bin/bash

sudo docker-compose down -v

gradle clean
gradle build

sudo docker-compose up

#docker run --rm quay.io/keycloak/keycloak:21.1.1 start-dev --import-realm
