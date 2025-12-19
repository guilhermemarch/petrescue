# Pet Reports — Animal Rescue Reporting System

[![Java](https://img.shields.io/badge/Java-17-red?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-yellowgreen?logo=spring&logoColor=white)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Hibernate](https://img.shields.io/badge/Hibernate-JPA-brown?logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Cloudinary](https://img.shields.io/badge/Cloudinary-image%20storage-lightblue?logo=cloudinary)](https://cloudinary.com/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green?logo=swagger)](https://swagger.io/)

A complete system for reporting lost and found pets using intelligent geolocation. Helps the community of Cerro Largo report, search, and rescue pets using interactive maps, cloud image uploads, and advanced location-based search.

---

## Features

### Report Management
- Create reports with photo, description, and GPS location
- Image upload via Cloudinary (cloud storage)
- Precise geolocation (latitude/longitude)
- Advanced search by type, neighborhood, date, status
- Mark as resolved when pet is found
- Edit and delete own reports

### Authentication and Authorization
- User registration with roles (USER/ADMIN)
- Secure JWT login
- BCrypt encrypted passwords
- Role-based access control

### Visualization and Search
- Interactive map with lost pet markers
- Intelligent filters:
  - Animal type (dog, cat, others)
  - Neighborhood/location
  - Sighting date
  - Status (active/resolved)
- Geographic proximity search

### Administration
- Admin panel to manage all reports
- Content moderation
- Regional report statistics

---

## Screenshots

![Login and Registration Screen](imagens/1.png)

*Secure JWT authentication, allowing new user registration and login to access the system.*

---

![Reports Map](imagens/2.png)

*Interactive visualization of all pet reports on map with georeferenced markers.*

---

![Report Creation Form](imagens/3.png)

*Intuitive interface to create reports with image upload, description, animal type, and GPS location.*

---

![Report Details](imagens/4.png)

*Complete report view with image, pet information, map location, and resolution options.*

---

## Architecture

### System Architecture

```mermaid
graph TB
    subgraph "Client Layer"
        Web[Web App<br/>React]
        Mobile[Mobile Web]
    end
    
    subgraph "Application Layer"
        API[Pet Reports API<br/>Spring Boot 3.2.3<br/>Port 8080]
        Security[Spring Security<br/>JWT Auth]
        Swagger[Swagger UI<br/>API Docs]
    end
    
    subgraph "Data Layer"
        DB[(PostgreSQL 14<br/>Reports & Users)]
        JPA[Hibernate/JPA<br/>ORM]
    end
    
    subgraph "External Services"
        Cloudinary[Cloudinary API<br/>Image Storage]
        Maps[Maps Service<br/>Geocoding]
    end
    
    Web -->|HTTP/REST| API
    Mobile -->|HTTP/REST| API
    API --> Security
    API --> Swagger
    Security --> API
    API --> JPA
    JPA --> DB
    API -->|Upload Images| Cloudinary
    API -->|Geocoding| Maps
    Cloudinary -.->|Image URLs| Web
    Cloudinary -.->|Image URLs| Mobile
```

### Data Model (ER Diagram)

```mermaid
erDiagram
    User ||--o{ Report : creates
    User {
        uuid id PK
        string name
        string email UK
        string password
        enum role "USER, ADMIN"
        timestamp created_at
        timestamp updated_at
    }
    
    Report {
        uuid id PK
        uuid user_id FK
        enum animal_type "DOG, CAT, BIRD, OTHER"
        string description
        string image_url
        decimal latitude
        decimal longitude
        string neighborhood
        timestamp report_date
        boolean resolved
        timestamp created_at
        timestamp updated_at
    }
```

**Relationships:**
- One **User** can create multiple **Reports** (1:N)
- Each **Report** belongs to one **User**
- **Resolved** indicates if pet was found/rescued

### Report Creation Flow

```mermaid
sequenceDiagram
    actor User
    participant Frontend
    participant API
    participant Security as JWT Security
    participant DB as PostgreSQL
    participant Cloud as Cloudinary
    
    User->>Frontend: Fill form<br/>(photo, description, location)
    Frontend->>Frontend: Get GPS coordinates<br/>(browser)
    
    Frontend->>+API: POST /api/reports<br/>Authorization: Bearer {token}
    API->>Security: Validate JWT token
    Security-->>API: Token valid (userId)
    
    alt Image Upload
        API->>+Cloud: Upload image
        Cloud-->>-API: Image URL
    end
    
    API->>API: Create Report object<br/>{type, desc, img_url, lat, lng}
    API->>DB: Save report
    DB-->>API: Report created (id, timestamp)
    
    API-->>-Frontend: 201 Created<br/>{id, imageUrl, location}
    Frontend->>Frontend: Add marker on map
    Frontend-->>User: Report created successfully
```

---

## Technologies

### Backend

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Primary language |
| Spring Boot | 3.2.3 | Web framework |
| Spring Security | 6.x | JWT authentication |
| Spring Data JPA | 3.x | ORM persistence |
| Hibernate | 6.x | ORM implementation |
| PostgreSQL | 14 | Relational database |
| Lombok | 1.18.x | Boilerplate reduction |
| Maven | 3.9.6 | Build and dependencies |

### External Services

| Service | Purpose |
|---------|---------|
| Cloudinary | Image storage and CDN |
| Swagger/OpenAPI | Interactive API documentation |
| Docker | Containerization and deployment |

### Security

- JWT (JSON Web Tokens) for stateless authentication
- BCrypt for password hashing
- CORS configured for allowed origins
- Role-Based Access Control (USER/ADMIN)

---

## Installation

### Prerequisites

- Java 17+
- Maven 3.9+
- PostgreSQL 14+
- Docker (optional, for deployment)
- Cloudinary account (free tier available)

### Database Configuration

#### Option 1: Local PostgreSQL

```bash
# Install PostgreSQL
# Windows: https://www.postgresql.org/download/windows/
# Linux: sudo apt install postgresql

# Create database
psql -U postgres
CREATE DATABASE petrescue_db;
\q
```

#### Option 2: Docker

```bash
cd pet-reports-backend
docker-compose up -d
```

### Cloudinary Configuration

1. Create free account at [Cloudinary](https://cloudinary.com/)
2. Access Dashboard and copy:
   - Cloud Name
   - API Key
   - API Secret

### Application Installation

```bash
# 1. Clone repository
git clone https://github.com/your-username/pet-reports.git
cd pet-reports/pet-reports-backend

# 2. Configure application.properties
# Edit src/main/resources/application.properties
# See Configuration section below

# 3. Build project
mvn clean install

# 4. Run
mvn spring-boot:run

# Or run JAR
java -jar target/pet-reports-backend-1.0.0.jar
```

---

## Usage

### Complete Flow Example

#### 1. Register user

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Silva",
    "email": "maria@example.com",
    "password": "senha123",
    "role": "USER"
  }'
```

**Response:**
```json
{
  "id": "a7b3c1d2-e5f6-4a9b-8c7d-1234567890ab",
  "name": "Maria Silva",
  "email": "maria@example.com",
  "role": "USER",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "maria@example.com",
    "password": "senha123"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": "a7b3c1d2-e5f6-4a9b-8c7d-1234567890ab",
  "email": "maria@example.com",
  "role": "USER"
}
```

#### 3. Create report (with image)

```bash
curl -X POST http://localhost:8080/api/reports \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -F "animalType=DOG" \
  -F "description=Mixed breed dog, brown coat, lost near the park" \
  -F "latitude=-28.1234" \
  -F "longitude=-54.9876" \
  -F "neighborhood=Centro" \
  -F "image=@/path/to/dog-photo.jpg"
```

**Response:**
```json
{
  "id": "b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc",
  "userId": "a7b3c1d2-e5f6-4a9b-8c7d-1234567890ab",
  "animalType": "DOG",
  "description": "Mixed breed dog, brown coat, lost near the park",
  "imageUrl": "https://res.cloudinary.com/yourcloud/image/upload/v123456/pets/abc123.jpg",
  "latitude": -28.1234,
  "longitude": -54.9876,
  "neighborhood": "Centro",
  "reportDate": "2025-12-18T23:30:00Z",
  "resolved": false,
  "createdAt": "2025-12-18T23:30:15Z"
}
```

#### 4. Search reports with filters

```bash
# Search for unsolved dogs in Centro neighborhood
curl -X GET "http://localhost:8080/api/reports/search?type=DOG&neighborhood=Centro&resolved=false" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**Response:**
```json
{
  "total": 5,
  "reports": [
    {
      "id": "b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc",
      "animalType": "DOG",
      "description": "Mixed breed dog...",
      "imageUrl": "https://res.cloudinary.com/...",
      "latitude": -28.1234,
      "longitude": -54.9876,
      "neighborhood": "Centro",
      "resolved": false
    }
  ]
}
```

#### 5. Mark report as resolved

```bash
curl -X PATCH http://localhost:8080/api/reports/b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc/resolve \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**Response:**
```json
{
  "id": "b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc",
  "resolved": true,
  "resolvedAt": "2025-12-19T10:15:00Z",
  "message": "Pet found! Thanks for helping the community."
}
```

#### 6. Delete report (owner or admin only)

```bash
curl -X DELETE http://localhost:8080/api/reports/b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## API Endpoints

### Authentication

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/auth/register` | Register new user | No |
| `POST` | `/api/auth/login` | Login and get JWT | No |

### Reports

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/reports` | Create report with image | USER |
| `GET` | `/api/reports` | List all reports | USER |
| `GET` | `/api/reports/{id}` | Get report by ID | USER |
| `GET` | `/api/reports/search` | Search with advanced filters | USER |
| `PATCH` | `/api/reports/{id}/resolve` | Mark as resolved | USER (owner) |
| `PUT` | `/api/reports/{id}` | Update report | USER (owner) |
| `DELETE` | `/api/reports/{id}` | Delete report | USER (owner) or ADMIN |

### Search Parameters (`/api/reports/search`)

| Parameter | Type | Values | Description |
|-----------|------|--------|-------------|
| `type` | string | `DOG`, `CAT`, `BIRD`, `OTHER` | Animal type |
| `neighborhood` | string | Free text | Neighborhood/location |
| `resolved` | boolean | `true`, `false` | Resolution status |
| `startDate` | date | ISO 8601 | Start date |
| `endDate` | date | ISO 8601 | End date |
| `latitude` | decimal | -90 to 90 | Latitude for proximity search |
| `longitude` | decimal | -180 to 180 | Longitude for proximity search |
| `radius` | integer | km | Search radius (combined with lat/lng) |

### Complete Documentation

Access interactive Swagger documentation:
- **Local:** `http://localhost:8080/swagger-ui.html`
- **Production:** `https://your-api.com/swagger-ui.html`

---

## Configuration

### application.properties

```properties
# Server
server.port=8080

# Database PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/petrescue_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT
jwt.secret=your-secret-key-min-256-bits-change-in-production
jwt.expiration=86400000

# Cloudinary
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret
cloudinary.folder=pet-reports

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# CORS
cors.allowed-origins=http://localhost:3000,http://localhost:4200
```
