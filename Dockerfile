FROM ubuntu:latest

LABEL Maintainer="admin" Email="admin123@gmail.com"

# Install necessary packages
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    curl \
    && apt-get clean

# Verify Java installation
RUN java -version

# Set working directory
WORKDIR /opt

# Download and extract Tomcat
RUN curl -O https://downloads.apache.org/tomcat/tomcat-8/v8.5.58/bin/apache-tomcat-8.5.58.tar.gz
RUN tar xzvf apache-tomcat-8.5.58.tar.gz -C /opt/
RUN mv /opt/apache-tomcat-8.5.58 /opt/tomcat

# Copy the WAR file to the webapps directory
WORKDIR /opt/tomcat/webapps
COPY target/*.war /opt/tomcat/webapps/webapp.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
ENTRYPOINT ["/opt/tomcat/bin/catalina.sh", "run"]
