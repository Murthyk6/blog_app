# 📝 Blog Application — Spring Boot

> A full-stack blog platform built with Spring Boot 3, featuring user authentication, post management, and a server-rendered UI. Demonstrates production-ready Java backend patterns including Spring Security, JPA with MySQL, and input validation.

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.2-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3-005F0F?style=flat-square&logo=thymeleaf&logoColor=white)

---

## Features

- **User Authentication** — Registration, login, and logout with Spring Security 6
- **Role-based Access** — Secure routes, author-only post management
- **Blog CRUD** — Create, read, update, delete posts with validation
- **Server-side Rendering** — Thymeleaf templates with Spring Security integration
- **Input Validation** — Bean validation with `@Valid` and custom error messages
- **Persistent Storage** — Spring Data JPA with MySQL

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.1.2 |
| Security | Spring Security 6 |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8.0 |
| Templating | Thymeleaf 3 + Security Extras |
| Build | Maven |

---

## Getting Started

### Prerequisites

- Java 17+
- MySQL 8.0+
- Maven 3.6+

### Setup

```bash
git clone https://github.com/Murthyk6/blog_app.git
cd blog_app
```

Configure `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Run:

```bash
./mvnw spring-boot:run
```

App starts at `http://localhost:8080`

---

## Project Structure

```
src/main/java/com/murthy/blog/
├── controller/     # MVC controllers
├── model/          # JPA entities
├── repository/     # Spring Data repositories
├── service/        # Business logic
└── security/       # Spring Security config
src/main/resources/
├── templates/      # Thymeleaf views
└── application.properties
```

---

## Key Concepts Demonstrated

- Spring Security authentication and authorization flow
- JPA entity relationships and repository pattern
- Thymeleaf template inheritance and fragment reuse
- Bean Validation (`@NotBlank`, `@Size`, custom constraints)
- MVC pattern with service layer separation

---

> Built as part of backend engineering practice at [MountBlue Technologies](https://github.com/Murthyk6). Reflects Java/Spring Boot skills used in production at Ubona Technologies.
