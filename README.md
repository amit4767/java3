TO make production Ready application.

1.	Need to update yml file with prod and prepod environment details.
2.  Define profile base property in the YML file .
3.  Define separeate YML file to test resoureces.
4.  Add log4j.rootLogger to maintain the logger level.
5.	Need to add docker file if we are going to use docker container .
6.	Need to have db connenction detail which has DAo layer where we need to maintain the ACID property.
7.  Need to spring security for authz.
8.  Need to health check for db and etc . So that from the health URL we can monitor the application.
9.  We can use token base authz.
  
