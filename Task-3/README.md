# Task 3

A simple RESTful API

# Implementation

## Data
All data classes are extended from BaseData class.  
BaseData holds all the basic information which everyother resource class should have.  
Such as id, created, updated and HATEOAS links.  

## Services
Services which actually manage resources are extended for BaseService class.  
BaseService class uses generic typing to determine data type.  
BaseService should implement all the basic operations.  
Rest of the services are extended from BaseService class and use Singleton pattern.  
Any service specific methods can be implemented in those classes.  

## Resources
All the actual RESTful implementations are implemented in Resource classes.  
Any new resource class should be added to Ties456RestMain class' classes HashSet.  
Otherwise it won't get loaded when the server is started.


# Running locally

0. TODO better guide d_O_b
1. Import Maven project
2. Run Project as Server

# Usage

1. Use [Postman](https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop) for testing
2. Base URL is http://localhost:<tomcat_port>/webapi
3. To get TestResources use http://localhost:<tomcat_port>/webapi/resources
