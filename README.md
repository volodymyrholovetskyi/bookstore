# Online Bookstore

* [General info](#general-info)
* [Requirement](#requirements)
* [API Documentation](#api-documentation)
* [Technologies](#technologies)

## General info

Online store for the sale and management of books

## Requirements:

### Admin

> * should be able to initialize and manipulate catalogs
> * should be able to manipulate the status order

### Client

> * should be able to add books to the basket
> * should be able to register
> * should be able to order books
> * should receive a special discount when ordering a larger quantity of the books

### App

> * the program should search for an abandoned order and delete it automatically

## API Documentation:

| Operation                                    | URL template                                         |
| -------------------------------------------- | ---------------------------------------------------- |
| Initialization of the database with books    | POST /initialization                                 |
| Get a list of authors                        | GET /authors                                         |
| Get a list of catalogs by specified criteria | GET /catalog?title=<title_name>&author=<author_name> |
| Get a book by ID                             | GET /catalog/{id}                                    |
| Create a new book                            | POST /catalog                                        |
| Add a cover to book                          | PUT /catalog/{id}/cover                              |
| Remove a book by ID                          | DELETE /catalog/{id}                                 |
| Remove a book cover by ID                    | DELETE /catalog/{id}                                 |
| Get a list of orders                         | GET /orders                                          |
| Get order details                            | GET /orders/{id}                                     |
| Create a new order                           | POST /orders                                         |
| Update a order status                        | PUT /orders/{id}/status                              |
| Get a book cover by ID                       | GET /uploads/{id}                                    |
| Upload a cover book file                     | GET /uploads/{id}/file                               |
| User registration                            | POST /users                                          |

## Technologies:

* Java 11
* Spring Boot
* Spring Security
* Spring Data
* Spring Scheduled
* Hexagonal Architecture
* JUnit 5
* AssertJ
* Mockito
* Lombok
* PostgresQL
* Travis CI/CD
* Docker Compose
* Cloud Heroku 
* Swagger
* Git
