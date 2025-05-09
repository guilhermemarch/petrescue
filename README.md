# Animal Rescue Reporting System

A complete application to help people report and find lost animals using geolocation and community collaboration, focused on the city of 'Cerro Largo'.

## Features

-  Secure user authentication with JWT
-  Geolocation-based pet reports
-  Image upload with Cloudinary
-  Advanced search with filters
-  Map integration ready
-  User role management
-  RESTful API with Swagger

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Security + JWT
- PostgreSQL
- Hibernate (JPA)
- Cloudinary
- Swagger (OpenAPI)

### Frontend (Coming Soon)
- React (Vite)
- TailwindCSS
- React Router
- Axios
- Leaflet.js
- React Hook Form

## Quick Start

1. Clone the repository
2. Configure your environment variables:
   - Database connection
   - Cloudinary credentials
   - JWT secret
3. Run the backend:
   ```bash
   mvn spring-boot:run
   ```
4. Access the API documentation at `http://localhost:8080/swagger-ui.html`

## Documentation

- [Requirements](requirements.md) - Project requirements and implementation status
- [Backend Guide](backend-guide.md) - Backend implementation details
- [Frontend Guide](frontend-guide.md) - Frontend development guide
- [Database Model](database.md) - Database schema and relationships
- [API Endpoints](api.md) - Complete API documentation

## API Testing

A Postman collection is available in the root directory (`pet-reports.postman_collection.json`). Import it to test all endpoints.

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
