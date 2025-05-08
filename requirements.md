# Requirements Document

## Functional Requirements

| Code | Description | Status |
|------|-------------|--------|
| RF01 | User registration and login | ✅ Implemented with JWT |
| RF02 | Authenticated users can report found pets | ✅ Implemented with file upload |
| RF03 | Reports contain photo, description, location, and date | ✅ Implemented with Cloudinary |
| RF04 | Map displays geolocated reports | ✅ API endpoints ready |
| RF05 | Search by animal type, neighborhood, or date | ✅ Implemented with filters |
| RF06 | Mark report as "reunited" | ✅ Implemented as PATCH endpoint |
| RF07 | Admin can delete invalid/spam reports | ✅ Implemented with role check |
| RF08 | JWT Authentication protection | ✅ Implemented with Spring Security |
| RF09 | Images stored on external service | ✅ Implemented with Cloudinary |

## Non-Functional Requirements

| Code | Description | Status |
|------|-------------|--------|
| RNF01 | Responsive UI | 🔄 Frontend pending |
| RNF02 | RESTful API with Swagger | ✅ Implemented |
| RNF03 | Logging and error handling | ✅ Implemented with GlobalExceptionHandler |
| RNF04 | Secure against XSS, CSRF, SQLi | ✅ Implemented with Spring Security |
| RNF05 | Deployable in cloud (Vercel, Render, etc.) | 🔄 Deployment pending |

## Implementation Details

### Security
- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control
- CSRF protection disabled for API
- Stateless session management

### Data Storage
- PostgreSQL for relational data
- Cloudinary for image storage
- Proper indexing for performance
- Data validation and constraints

### API Features
- RESTful endpoints
- Swagger documentation
- Request/Response DTOs
- Proper error handling
- File upload support
- Geolocation search

### Testing
- Postman collection available
- Error scenarios covered
- Authentication flow tested
- File upload tested

Legend:
- ✅ Implemented
- 🔄 In Progress/Pending
