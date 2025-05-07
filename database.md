#  Database Model
 

### `users`

| Field      | Type    |
|------------|---------|
| id         | UUID PK |
| name       | TEXT    |
| email      | TEXT UNIQUE |
| password   | TEXT (hashed) |
| role       | TEXT ("USER" or "ADMIN") |

### `reports`

| Field        | Type     |
|--------------|----------|
| id           | UUID PK  |
| user_id      | UUID FK  |
| animal_type  | TEXT     |
| description  | TEXT     |
| image_url    | TEXT     |
| latitude     | DOUBLE   |
| longitude    | DOUBLE   |
| date_found   | TIMESTAMP |
| is_resolved  | BOOLEAN  |
