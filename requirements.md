#  Requirements Document

##  Functional Requirements

| Code | Description |
|------|-------------|
| RF01 | User registration and login |
| RF02 | Authenticated users can report found pets |
| RF03 | Reports contain photo, description, location, and date |
| RF04 | Map displays geolocated reports |
| RF05 | Search by animal type, neighborhood, or date |
| RF06 | Mark report as "reunited" |
| RF07 | Admin can delete invalid/spam reports |
| RF08 | JWT Authentication protection |
| RF09 | Images stored on external service |

##  Non-Functional Requirements

| Code | Description |
|------|-------------|
| RNF01 | Responsive UI |
| RNF02 | RESTful API with Swagger |
| RNF03 | Logging and error handling |
| RNF04 | Secure against XSS, CSRF, SQLi |
| RNF05 | Deployable in cloud (Vercel, Render, etc.) |
