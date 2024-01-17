# Spring Boot Todo Project

Spring Boot Web application to provide REST API in JSON

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/TolunayToprakkaya/spring-boot-todo-project.git
   ```
2. Install NPM packages
   ```sh
   npm install
   ```
3. Build application
   ```sh
   java -jar .\target\TodoProject-0.0.1-SNAPSHOT.jar
   ```
4. Run the test suite
   ```sh
   mvn clean test
   ```   
## Tech Stacks

This project template uses:

* Java 21
* Spring Boot
* [Couchbase](https://www.couchbase.com/) for database migrations
* [Spring Data Couchbase](https://spring.io/projects/spring-data-couchbase/) for database access
* [Spring Web](https://spring.io/guides/gs/serving-web-content/) to serve HTTP requests
* [Spring Security](https://spring.io/projects/spring-security/) for authentication
* [Swagger](https://swagger.io/) for api documentation
* [Maven](https://maven.apache.org/) to build and, in dev-mode, run the application with hot reload
* [Docker](https://www.docker.com/) for container
* [Junit](https://junit.org/junit5/) for testing

<!-- API DESCRIPTIONS -->
## API Descriptions

### Get Token
```
POST /api/user/token
Accept: application/json
Content-Type: application/json
{
    "username": "admin",
    "password": "admin123"
}

Response: HTTP 200
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0Ijo....",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0Ijox....",
    "error": null
}
```

### Refresh Token
```
Get /api/user/refreshToken
Accept: application/json
Content-Type: application/json

Response: HTTP 200
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0Ijo....",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0Ijox....",
    "error": null
}
```

### Retrieve a list of users (Only for Admin users)
```
POST /api/user
Accept: application/json
Content-Type: application/json

Response: HTTP 200
Content: List of users
```

### Retrieve a list of user's detail
```
POST /api/user/{userId}
Accept: application/json
Content-Type: application/json

Response: HTTP 200
Content: List of user's detail 
```

### Create a user
```
POST /api/user/register
Accept: application/json
Content-Type: application/json

{
    "name": "Test",
    "lastname": "Test",
    "email": "test@gmail.com",
    "username": "test",
    "password": "test123",
    "roles": [
        "ADMIN",
        "USER"
    ]
}

RESPONSE: HTTP 201 (Created)
{
    "id": 8472554893112592148,
    "name": "Test",
    "lastname": "Test",
    "username": "test",
    "email": "test@gmail.com",
    "password": "$2a$10$v/LzvauEObgZ4/s6wgUvquYBFolYKxxonjWBI9BIzKUOqAwN3FepO",
    "roles": [
        "ADMIN",
        "USER"
    ],
    "createDate": "2024-01-17T14:52:30.4101214",
    "updateDate": null
}
```
### Update a todo
```
POST /api/user/updateUser
Accept: application/json
Content-Type: application/json

{
    "id": "8472554893112592148",
    "name": "Test",
    "lastname": "Test",
    "email": "test@gmail.com",
    "username": "test",
    "password": "test123",
    "roles": [
        "USER"
    ]
}

RESPONSE: HTTP 200 (OK)
{
    "id": 8472554893112592148,
    "name": "Test",
    "lastname": "Test",
    "username": "test",
    "email": "test@gmail.com",
    "password": "$2a$10$e.ct8GKZf67PZ39BRFISceFSe9YwF71U8yHxylebAZgk/4n/J95cq",
    "roles": [
        "USER"
    ],
    "createDate": "2024-01-17T14:52:30.41",
    "updateDate": "2024-01-17T16:52:47.2201853"
}
```
### Delete a user
```
POST /api/user/deleteUser/{userId}
Accept: application/json
Content-Type: application/json

Response: HTTP 200
Content: Deleted user 
```

### Retrieve a list of todos (Only for Admin users)
```
POST /api/todo
Accept: application/json
Content-Type: application/json

Response: HTTP 200
Content: List of todos 
```

### Retrieve a list of user's todos
```
POST /api/todo/{userId}
Accept: application/json
Content-Type: application/json

Response: HTTP 200
Content: List of user's todos 
```

### Create a todo
```
POST /api/todo/createTodo
Accept: application/json
Content-Type: application/json

{
    "name": "Study Math",
    "categoryList": [
        "Work"
    ]
}

RESPONSE: HTTP 201 (Created)
{
    "id": 5804956094207511732,
    "name": "Study Math",
    "categoryList": [
        "Work"
    ],
    "todoStatus": "IN_PROGRESS",
    "userId": "6349004945689692778",
    "createDate": "2024-01-17T15:02:25.8945173",
    "updateDate": null
}
```

### Update a todo
```
POST /api/todo/updateTodo
Accept: application/json
Content-Type: application/json

{
    "id": "5804956094207511732",
    "name": "Study Math",
    "categoryList": [
        "Work"
    ],
    "todoStatus": "DONE"
}

RESPONSE: HTTP 200 (OK)
{
    "id": 5804956094207511732,
    "name": "Study Math",
    "categoryList": [
        "Work"
    ],
    "todoStatus": "DONE",
    "userId": "6349004945689692778",
    "createDate": "2024-01-17T15:02:25.894",
    "updateDate": "2024-01-17T17:02:37.6081355"
}
```
### Delete a todo
```
POST /api/todo/deleteTodo/{todoId}
Accept: application/json
Content-Type: application/json

Response: HTTP 200
Content: Deleted todo 
```

### To view Swagger API docs

Run the server and browse to localhost:8000/swagger-ui/index.html/
