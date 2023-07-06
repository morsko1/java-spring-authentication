# Java Spring JWT Authentication

This is an example demonstrating how to implement JWT authentication using Java Spring Security. JSON Web Tokens (JWT) are a popular method for authentication and authorization in modern web applications. In this example, we will use Spring Security to secure our application and generate JWT tokens for authentication.


## Getting Started

1. Clone repository:
    ```
    git clone <repository_url>
    ```

2. Navigate to the project directory:
    ```
    cd java-spring-authentication
    ```

3. Build the project using Maven:
    ```
    mvn clean install
    ```

4. Run the application:
    ```
    mvn spring-boot:run
    ```


## Endpoints

The following endpoints are available:

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and generate a JWT token
- `GET /api/ping` - Access a public endpoint
- `GET /api/data` - Access a protected endpoint (requires a valid JWT token)

## Usage

### Register a New User

To register a new user, send a `POST` request to `/api/auth/register` with the following JSON payload:

```json
{
  "username": "john_doe",
  "password": "password123"
}
```

### Authenticate and Generate JWT Token

To authenticate and generate a JWT token, send a POST request to `/api/auth/login` with the following JSON payload:

```json
{
  "username": "john_doe",
  "password": "password123"
}
```

Upon successful authentication, JWT token will be set in cookie.


### Access Protected Endpoints
When cookies are set you are able to access protected endpoint `/api/data`


## Configuration

The application can be configured through the `application.properties` file located in the `config` directory. Here is an example:

```
server.port=8888
spring.datasource.url=jdbc:postgresql://localhost:5432/<db_name>
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

application.security.jwtSecret=<secret>
application.security.jwtExpirationMs=86400000
application.security.jwtCookieName=<cookie_name>
```