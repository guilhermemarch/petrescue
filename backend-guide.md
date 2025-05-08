# Backend Guide

## Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring Security + JWT
- PostgreSQL
- Hibernate (JPA)
- Cloudinary (for image storage)
- Swagger (OpenAPI)
- Maven

## Package Structure

```
com.petrescue
├── controller
│   ├── AuthController
│   └── ReportController
├── service
│   ├── impl
│   │   ├── AuthServiceImpl
│   │   ├── ReportServiceImpl
│   │   └── UserServiceImpl
│   ├── AuthService
│   ├── ReportService
│   └── UserService
├── repository
│   ├── ReportRepository
│   └── UserRepository
├── model
│   ├── Report
│   └── User
├── dto
│   ├── auth
│   │   ├── AuthResponse
│   │   ├── LoginRequest
│   │   └── RegisterRequest
│   └── report
│       ├── CreateReportRequest
│       └── ReportResponse
├── security
│   ├── JwtAuthenticationFilter
│   ├── JwtTokenProvider
│   ├── SecurityConfig
│   └── CustomUserDetailsService
└── exception
    ├── GlobalExceptionHandler
    ├── ResourceNotFoundException
    └── FileUploadException
```

## Completed Tasks

- [x] Init Spring Boot project
- [x] Create entities and DTOs
- [x] Implement repositories
- [x] JWT-based authentication
- [x] Controllers and services
- [x] File upload to Cloudinary
- [x] Add Swagger documentation
- [x] Implement error handling
- [x] Add security configuration
- [x] Create Postman collection

## Running the Application

1. Ensure you have Java 17 and Maven installed
2. Configure your PostgreSQL database in `application.properties`
3. Configure Cloudinary credentials in `application.properties`
4. Run `mvn spring-boot:run`
5. Access Swagger UI at `http://localhost:8080/swagger-ui.html`

## API Documentation

For detailed API documentation, see [api.md](api.md) or the Postman collection in the root directory.
