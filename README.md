# hitech-health-api

This is the HiTech Health coding test services API.
This API was made in Java Maven + Rest + JDBC + MariaDB

Demo version available on AWS: [Demo](http://elasticbeanstalk-eu-west-1-100101151868.s3-website-eu-west-1.amazonaws.com)

Admin access:

Email: admin@hitech-health.com
Password: admin

You can run it in any IDE of your choice as it is a MAVEN project.

## Create DataBase
Before importing the project, you need to create the database (MariaDB). The schema is found in the folder ... src \ main \ resources \ hitech in data.sql

## Change access settings to the API database
Before running the project, you need to change the access settings to the API database.


Go to ... src \ main \ resources \

Open the file: config.properties

There are four attributes:

config.classe= Database connection drive class

config.pwd= Database password

config.url= Connection string to the database

config.user= Access User


# After that, you can deploy the application to a server with a Java container like Tomcat.


