# Pet Reports — Animal Rescue Reporting System

A complete application to help people report and find lost animals using geolocation, image uploads, and community collaboration, initially focused on the city of Cerro Largo.

## Features

- Secure user authentication with JWT
- Pet reports with map location
- Image uploads via Cloudinary (you must put your API KEY)
- Advanced search with filters (type, neighborhood, date, status)
- Interactive map view of reports
- User role management (admin, user)
- RESTful API with Swagger documentation
- Responsive UI (in development)

##  Technologies

**Backend**
- Java 17, Spring Boot 3.2.3, Spring Security (JWT)
- PostgreSQL, Hibernate (JPA)
- Cloudinary (image storage)
- Swagger (OpenAPI)
- Maven

**Frontend**
- React (Vite), TailwindCSS, React Router
- Axios, Leaflet.js, React Hook Form

##  Getting Started

### Backend

1. Install Java 17 and Maven.
2. Configure PostgreSQL database and Cloudinary credentials in `application.properties`.
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Access API documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Frontend

1. Install dependencies with `npm install` or `pnpm install` in the `pet-reports-frontend` directory.
2. Configure `NEXT_PUBLIC_API_URL` if needed (localhost is configured).
3. Run:
   ```bash
   npm run dev
   ```
4. Access the UI at [http://localhost:3000](http://localhost:3000)

##  Documentation

- [Requirements](requirements.md)
- [Backend Guide](backend-guide.md)
- [Frontend Guide](frontend-guide.md)
- [Database Model](database.md)
- [API Endpoints](api.md)

##  Data Model

**Users**
- id (UUID), name, email, password (BCrypt), role (USER/ADMIN)

**Reports**
- id (UUID), user, animal type, description, image, latitude, longitude, date, status (resolved)

See details in [database.md](database.md).

##  Key Endpoints

- `POST /api/auth/register` — User registration
- `POST /api/auth/login` — User login
- `POST /api/reports` — Create report (with image and location)
- `GET /api/reports/search` — Search reports with filters
- `PATCH /api/reports/:id/resolve` — Mark report as resolved
- `DELETE /api/reports/:id` — Delete report (admin/user)

Complete examples and response formats in [api.md](api.md).

##  Testing

- Postman collection available: `pet-reports.postman_collection.json`
- Authentication, upload, and search flows tested


