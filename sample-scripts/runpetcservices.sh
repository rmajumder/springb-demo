#!/bin/bash

echo packaging and running visit container

cd ~/Documents/intuit/springb-demo/spring-petclinic-visit
mvn clean package -Dmaven.test.skip=true
docker build -t petclinic-visit .
docker run -d -p 8082:8082 petclinic-visit

echo packaging and running vet container

cd ~/Documents/intuit/springb-demo/spring-petclinic-vet
mvn clean package -Dmaven.test.skip=true
docker build -t petclinic-vet .
docker run -d -p 8085:8085 petclinic-vet

echo packaging and running pet container

cd ~/Documents/intuit/springb-demo/spring-petclinic-pet
mvn clean package -Dmaven.test.skip=true
docker build -t petclinic-pet .
docker run -d -p 8084:8084 petclinic-pet

echo packaging and running owner container

cd ~/Documents/intuit/springb-demo/spring-petclinic-owner
mvn clean package -Dmaven.test.skip=true
docker build -t petclinic-owner .
docker run -d -p 8083:8083 petclinic-owner

