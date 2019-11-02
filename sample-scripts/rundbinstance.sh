#!/bin/bash

docker run -i -p 127.0.0.1:3306:3306 -e MYSQL_ROOT_PASSWORD=petclinic -t cytopia/mysql-5.7

