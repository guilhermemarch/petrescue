https://github.com/guilhermemarch/auth-lib
https://github.com/guilhermemarch/distributed-services-spring
https://github.com/guilhermemarch/event-ticket-distributed-app
https://github.com/guilhermemarch/retail-sys-api
https://github.com/guilhermemarch/rain-alert-api



# API Endpoints

## Authentication

### Register User
- **POST** `/api/auth/register`
- **Body:**
  ```json
  {
    "name": "guilherme",
    "email": "guilherme@gmail.com",
    "password": "minhasenha123"
  }
  ```
- **Response:** JWT token and user info

### Login
- **POST** `/api/auth/login`
- **Body:**
  ```json
  {
    "email": "guilherme@gmail.com",
    "password": "minhasenha123"
  }
  ```
- **Response:** JWT token and user info

## Reports

### Create Report
- **POST** `/api/reports`
- **Headers:** `Authorization: Bearer {token}`
- **Body:** `multipart/form-data`
  - `animalType`: String
  - `description`: String
  - `latitude`: Double
  - `longitude`: Double
  - `image`: File

### Get Report by ID
- **GET** `/api/reports/{id}`
- **Headers:** `Authorization: Bearer {token}`

### Get User Reports
- **GET** `/api/reports/user`
- **Headers:** `Authorization: Bearer {token}`

### Search Reports
- **GET** `/api/reports/search`
- **Headers:** `Authorization: Bearer {token}`
- **Query Parameters:**
  - `animalType`: String (optional)
  - `isResolved`: Boolean (optional)
  - `minLat`: Double (optional)
  - `maxLat`: Double (optional)
  - `minLng`: Double (optional)
  - `maxLng`: Double (optional)

### Mark Report as Resolved
- **PATCH** `/api/reports/{id}/resolve`
- **Headers:** `Authorization: Bearer {token}`

### Delete Report
- **DELETE** `/api/reports/{id}`
- **Headers:** `Authorization: Bearer {token}`

## Response Formats

### Auth Response
```json
{
  "token": "jwt-token",
  "email": "guilherme@gmail.com",
  "name": "guilherme",
  "role": "USER"
}
```

### Report Response
```json
{
  "id": "uuid",
  "animalType": "fish",
  "description": "encontrei um peixe perdido",
  "imageUrl": "https://cloudinary.com/image.jpg",
  "latitude": 40.7829,
  "longitude": -73.9654,
  "dateFound": "2024-05-07T16:01:22.006-03:00",
  "isResolved": false,
  "userId": "user-uuid"
}
```

## Error Responses

All endpoints may return the following error responses:

- **400 Bad Request**
  ```json
  {
    "field": "error message"
  }
  ```

- **401 Unauthorized**
  ```json
  {
    "error": "Invalid email or password"
  }
  ```

- **404 Not Found**
  ```json
  {
    "error": "Resource not found"
  }
  ```

- **500 Internal Server Error**
  ```json
  {
    "error": "Error message"
  }
  ```
