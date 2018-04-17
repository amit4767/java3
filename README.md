TO make production Ready application. We need take care of followings points.

1.	Need to update yml file with prod and prepod environment details.
2.  Define profile base property in the YML file.
3.  Define separeate YML in test/resource to test resources.
4.  Add log4j.rootLogger file to maintain the logger level.
5.	Need to add docker file if we are going to use docker container and we can define mutiple instance there.
6.	Need to have db connenction detail which has DAo layer where we need to maintain the ACID property.
7.  Need to spring security for authz.
8.  Need to health check for db and etc . So that from the health URL we can monitor the application.
9.  We can use token base authz.
10. We can use inMemormy data base for testing on Local and for integration testing.
11. Remove tomcat port from yml and enable spring actuator for health testing and disable sensitive to false
  
