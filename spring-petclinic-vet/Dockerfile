# Start with a base image containing Java runtime (mine java 8)
FROM openjdk:8u212-jdk-slim
# Add Maintainer Info
LABEL maintainer="rmajumder@berkeley.edu"
# Add a volume pointing to /tmp
VOLUME /tmp
# Make port 8080 available to the world outside this container
EXPOSE 8085
# The application's jar file (when packaged)
ARG JAR_FILE=target/spring-petclinic-vet-2.1.0.BUILD-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} petclinic-vet.jar
# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/petclinic-vet.jar"]
