# Database Model

## Tables

### `users`

| Field      | Type    | Constraints |
|------------|---------|-------------|
| id         | UUID    | PRIMARY KEY |
| name       | VARCHAR(255) | NOT NULL |
| email      | VARCHAR(255) | NOT NULL, UNIQUE |
| password   | VARCHAR(255) | NOT NULL |
| role       | VARCHAR(255) | NOT NULL, CHECK (role IN ('USER', 'ADMIN')) |

### `reports`

| Field        | Type     | Constraints |
|--------------|----------|-------------|
| id           | UUID     | PRIMARY KEY |
| user_id      | UUID     | NOT NULL, FOREIGN KEY REFERENCES users(id) |
| animal_type  | VARCHAR(255) | NOT NULL |
| description  | VARCHAR(255) | NOT NULL |
| image_url    | VARCHAR(255) | NOT NULL |
| latitude     | DOUBLE PRECISION | NOT NULL |
| longitude    | DOUBLE PRECISION | NOT NULL |
| date_found   | TIMESTAMP | NOT NULL |
| is_resolved  | BOOLEAN  | NOT NULL DEFAULT FALSE |

## Indexes

- `users_email_idx` on `users(email)`
- `reports_user_id_idx` on `reports(user_id)`
- `reports_date_found_idx` on `reports(date_found)`
- `reports_location_idx` on `reports(latitude, longitude)`

## Relationships

- One `User` can have many `Reports` (One-to-Many)
- Each `Report` belongs to one `User` (Many-to-One)

## Notes

- All timestamps are stored in UTC
- Passwords are stored as BCrypt hashes
- Image URLs are stored as Cloudinary URLs
- Location coordinates use double precision for accurate geolocation
