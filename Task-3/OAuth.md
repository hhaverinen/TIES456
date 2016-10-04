# OAuth2 Implementation

Main Server will serve as the Authorization server as we only support granting access tokens for
username and password implementing only the Resource Owner Password Credentials Grant.

[RFC Document for Implementation](https://tools.ietf.org/html/rfc6749#section-4.3)

## Getting Access Token
User can request access token from server with POST Method described below   

POST /token HTTP/1.1  
HOST: http://localhost/  
Authorization: Basic sadf4tASfu9k435D9asdF  
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=test&password=1234  

User must authenticate itself with Basic Authentication.  
Server denies every request if grant_type is not password.
Server will then respond with json.
### Access Token Request Response
```json
{
    "access_token":"afsO45MNo634dRE4TabMY3sdADA",
    "token_type":"password",
    "expires_in":"3600"
}
```
### Error Response
Response should be HTTP 400 Bad Request with following json content.
```json
{
    "error":"error_code",
    "error_description":"error description"
}
```
Possible Error Codes:
- invalid_request
    - The request is missing a required parameter, includes an invalid parameter value, includes a parameter more than once, or is otherwise malformed.


- unauthorized_client
    - All clients are applicable for password type request, so this is not needed
    - The client is not authorized to request an access token using this method.


- access_denied
    - Wrong password for username
    - The resource owner or authorization server denied the request.


- unsupported_response_type
    - if grant_type is not password
    - The authorization server does not support obtaining an access token using this method.


- invalid_scope
    - The requested scope is invalid, unknown, or malformed.


- server_error
    - Isn't necessary with this implementation since we are not doing redirects
    - The authorization server encountered an unexpected condition that prevented it from fulfilling the request. (This error code is needed because a 500 Internal Server Error HTTP status code cannot be returned to the client via an HTTP redirect.)


- temporarily_unavailable
    - Isn't necessary with this implementation since we are not doing redirects
    - The authorization server is currently unable to handle the request due to a temporary overloading or maintenance of the server.  (This error code is needed because a 503 Service Unavailable HTTP status code cannot be returned to the client via an HTTP redirect.)

## Requesting stuff with Access Token
After getting access token user can use it in the header of the request.

|Header Key|Header Value|
|----------|------------|
|Authorization|Bearer "access_token" |
