#!/bin/bash

sudo docker-compose down -v

gradle clean
gradle build

sudo docker-compose up
