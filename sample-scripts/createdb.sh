#!/bin/bash

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 < ~/Documents/intuit/springb-demo/spring-petclinic-owner/src/main/resources/db/mysql/schema.sql

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 petclinicowner < ~/Documents/intuit/springb-demo/spring-petclinic-owner/src/main/resources/db/mysql/data.sql

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 < ~/Documents/intuit/springb-demo/spring-petclinic-vet/src/main/resources/db/mysql/schema.sql

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 petclinicvet < ~/Documents/intuit/springb-demo/spring-petclinic-vet/src/main/resources/db/mysql/data.sql

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 < ~/Documents/intuit/springb-demo/spring-petclinic-pet/src/main/resources/db/mysql/schema.sql

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 petclinicpet < ~/Documents/intuit/springb-demo/spring-petclinic-pet/src/main/resources/db/mysql/data.sql

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 < ~/Documents/intuit/springb-demo/spring-petclinic-visit/src/main/resources/db/mysql/schema.sql

mysql --user=root --password=petclinic --port 3306 --host=127.0.0.1 petclinicvisit < ~/Documents/intuit/springb-demo/spring-petclinic-visit/src/main/resources/db/mysql/data.sql

