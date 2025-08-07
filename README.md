# ShortLink Microservice

This service allows a user to submit a URL and receive a shortened version. The shortened link will redirect to the original URL when accessed.

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/juliuszorlowski/shortlink-microservice.git
cd shortlink-microservice
```

### 2. Start the application

```bash
./mvnw spring-boot:run
```

### 3. Once running, the application will be available at:

```arduino
http://localhost:8080
```

## API Documentation

Swagger UI is available at:

```bash
http://localhost:8080/swagger-ui.html
```

## Using the service

### 1. Post link to get shortened link code

```bash
POST /shorten
```

Provide a JSON object with a valid link:

```bash
{
  "url": "http://www.example.com"
}
```

The link is stored in the database and is accessible by using the provided shortened link code.

```bash
{
  "shortened_url": "http://localhost:8080/rC0zDD"
}
```

### 2. Provide the shortened link code

```bash
GET /{code}
```

When a valid shortened link code is provided, the page is being redirected to the original link.

```bash
http://localhost:8080/rC0zDD
```

The number of times a particular short link code is being used is being tracked in the database.

## Technologies and libraries

* Spring Boot Starter Web
* Spring Boot Starter Test
* Spring Boot Devtools
* H2 Database
* Flyway
* Lombok
* Mapstruct
* Swagger-UI