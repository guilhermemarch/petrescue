
# API Endpoints

##  Auth

- `POST /auth/register` – Register user
- `POST /auth/login` – Login and receive JWT

##  Reports

- `GET /reports` – List all reports (with filters)
- `GET /reports/{id}` – Get report details
- `POST /reports` – Create a new report
- `PUT /reports/{id}/resolve` – Mark as reunited
- `DELETE /reports/{id}` – Delete (admin only)

##  User

- `GET /users/me` – Get current user info
