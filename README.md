# Pet Reports — Sistema de Relatórios de Resgate Animal

[![Java](https://img.shields.io/badge/Java-17-red?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-yellowgreen?logo=spring&logoColor=white)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Hibernate](https://img.shields.io/badge/Hibernate-JPA-brown?logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Cloudinary](https://img.shields.io/badge/Cloudinary-image%20storage-lightblue?logo=cloudinary)](https://cloudinary.com/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green?logo=swagger)](https://swagger.io/)
[![Maven](https://img.shields.io/badge/Maven-3.9.6-red?logo=apachemaven)](https://maven.apache.org/)
[![Lombok](https://img.shields.io/badge/Lombok-Java-orange?logo=lombok)](https://projectlombok.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)](https://www.docker.com/)

Aplicação completa para ajudar pessoas a reportar e encontrar animais perdidos usando geolocalização, upload de imagens e colaboração da comunidade, inicialmente focada na cidade de Cerro Largo.

## Screenshots

![Screenshot 1](imagens/1.png)
![Screenshot 2](imagens/2.png)
![Screenshot 3](imagens/3.png)
![Screenshot 4](imagens/4.png)

## Funcionalidades

- Autenticação segura de usuários com JWT
- Relatórios de pets com localização no mapa
- Upload de imagens via Cloudinary
- Busca avançada com filtros (tipo, bairro, data, status)
- Visualização interativa de relatórios no mapa
- Gerenciamento de roles de usuário (admin, user)
- API RESTful com documentação Swagger

## Tecnologias

### Backend
- Java 17, Spring Boot 3.2.3, Spring Security (JWT)
- PostgreSQL, Hibernate (JPA)
- Cloudinary (armazenamento de imagens)
- Swagger (OpenAPI)
- Maven

## Como Executar

### Backend

1. Instale Java 17 e Maven
2. Configure o banco de dados PostgreSQL e credenciais do Cloudinary em `application.properties`
3. Execute:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse a documentação da API: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Documentação

- [Modelo de Dados](database.md)
- [Endpoints da API](api.md)

## Modelo de Dados

### Usuários
- id (UUID), name, email, password (BCrypt), role (USER/ADMIN)

### Relatórios
- id (UUID), user, animal type, description, image, latitude, longitude, date, status (resolved)

Veja detalhes em [database.md](database.md).

## Endpoints Principais

- `POST /api/auth/register` — Registro de usuário
- `POST /api/auth/login` — Login de usuário
- `POST /api/reports` — Criar relatório (com imagem e localização)
- `GET /api/reports/search` — Buscar relatórios com filtros
- `PATCH /api/reports/:id/resolve` — Marcar relatório como resolvido
- `DELETE /api/reports/:id` — Deletar relatório (admin/user)

Exemplos completos e formatos de resposta em [api.md](api.md).

