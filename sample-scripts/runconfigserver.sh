#!/bin/bash

echo packaging and running config container

cd ~/Documents/intuit/springb-demo/spring-petclinic-config
mvn clean package -Dmaven.test.skip=true
docker build -t petclinic-config .
docker run -d -p 8888:8888 petclinic-config
