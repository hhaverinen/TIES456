# Task 3

A simple RESTful API

# Implementation

## Data
All data classes are extended from BaseData class.  
BaseData holds all the basic information which everyother resource class should have.  
Such as id, created, updated and HATEOAS links.  
Data specific attributes should all have getters and setters set when implemented.

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

# API Document
## Blogs
| Path          | Method | Parameters | Response | Failures | Description                |
|---------------|--------|------------|----------|----------|----------------------------|
|/blogs         | GET    | NONE       | 200      | NONE     | Gets all Blogs             |
|/blogs         | POST   | NONE       | 201      | 400      | Adds Blog from Message Body|
|/blogs/{blogId}| GET    | NONE       | 200      | NONE     | Gets blog by id            |
|/blogs/{blogId}| DELETE | NONE       | 204      | 404      | Removes blog by id         |
|/blogs/{blogId}| PUT    | NONE       | 200      | 404      | Updates blog by id         |

## Comments
| Path                               | Method | Parameters | Response | Failures | Description                 |
|------------------------------------|--------|------------|----------|----------|-----------------------------|
|/comments                           | GET    | NONE       | 200      | NONE     | Gets all Comments           |
|/comments/{commentId}               | GET    | NONE       | 200      | 404      | Gets Comment by id          |
|/blogs/{blogId}/comments            | GET    | NONE       | 200      | NONE     | Gets Blogs Comments         |
|/blogs/{blogId}/comments            | POST   | NONE       | 201      | 400      | Adds new Comment            |
|/blogs/{blogId}/comments/{commentId}| GET    | NONE       | 200      | 404      | Gets Comment from blog by id|

## Writers/Users
| Path                                  | Method | Parameters | Response | Failures | Description                           |
|---------------------------------------|--------|------------|----------|----------|---------------------------------------|
|/writers                               | GET    | NONE       | 200      | NONE     | Gets all Writers                      |
|/writers                               | POST   | NONE       | 201      | 400      | Adds new writer                       |
|/writers/{writerId}                    | GET    | NONE       | 200      | 404      | Gets Writer by id                     |
|/writers/{writerId}                    | DELETE | NONE       | 204      | 404      | Deletes Writer by id                  |
|/writers/{writerId}                    | PUT    | NONE       | 200      | 404      | Updates Writer by id                  |
|/writers/{writerId}/blogs              | GET    | NONE       | 200      | NONE     | Gets all blogs by writer              |
|/writers/{writerId}/blogs/{blogId}     | GET    | NONE       | 200      | NONE     | Gets all blog by writer and blog id   |
|/writers/{writerId}/comments           | GET    | NONE       | 200      | NONE     | Gets all comments by writer           |
|/writers/{writerId}/comments/{writerId}| GET    | NONE       | 200      | NONE     | Gets comment by writer and comment id |

# Running locally

0. TODO better guide d_O_b
1. Import Maven project
2. Run Project as Server

# Usage

1. Use [Postman](https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop) for testing
2. Base URL is http://localhost:<tomcat_port>/webapi
3. To get TestResources use http://localhost:<tomcat_port>/webapi/resources
