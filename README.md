# Pet Clinic Application Demo

##### Pet-Clinic Application Architecture
1. The front-end is a web application that is built with Spring Boot visualizations templates. In addition, this application is also responsible for data composition and orchestration with underlying microservices.
2. There are four microservices - vet, pet, visit, and owner. These small scale services are exposed with REST APIs and primarily they are used to do data management, parsing/formatting and maintaining data integrity according to business logic.
3. Each microservices has dedicated data storage (MySql) which can be maintained independently.
4. Actuator and Zipkin server is included to measure the health of the web-app and services.
5. A config server has been added to dynamically manage services' web address from the web-app.

![Alt text](./images/PetClinic-Architecture.png?raw=true "PetClinic Architecture")

##### Deployment
1. All the services and web-app can be packaged as JAR files and included a docker image with an exposed port. Once the images are containerized, it can be pushed to docker repository. Please find the included Dockerfile and scripts in this repository.
2. These services can be run in a cloud environment such as AWS-EKS and AWS RDS. Kubernetes deployment scripts are included for all the components and appropriate AWS IAM role-security is in place, those scripts cloud be used with command line tools - AWS CLI, eksctl, kubectl and deploy from a local machine.

![Alt text](./images/deployment-pipeline.png?raw=true "Deployment Pipeline")
 
