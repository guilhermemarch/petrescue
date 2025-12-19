# Pet Reports — Sistema de Relatórios de Resgate Animal

[![Java](https://img.shields.io/badge/Java-17-red?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-yellowgreen?logo=spring&logoColor=white)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Hibernate](https://img.shields.io/badge/Hibernate-JPA-brown?logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Cloudinary](https://img.shields.io/badge/Cloudinary-image%20storage-lightblue?logo=cloudinary)](https://cloudinary.com/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green?logo=swagger)](https://swagger.io/)

Sistema completo para relatórios de animais perdidos e encontrados usando geolocalização inteligente. Ajuda a comunidade de Cerro Largo a reportar, buscar e resgatar pets usando mapas interativos, upload de imagens na nuvem e busca avançada baseada em localização.

---

## Funcionalidades

### Gerenciamento de Relatórios
- Criar relatórios com foto, descrição e localização GPS
- Upload de imagens via Cloudinary (armazenamento em nuvem)
- Geolocalização precisa (latitude/longitude)
- Busca avançada por tipo, bairro, data, status
- Marcar como resolvido quando o pet for encontrado
- Editar e deletar próprios relatórios

### Autenticação e Autorização
- Registro de usuário com roles (USER/ADMIN)
- Login seguro com JWT
- Senhas criptografadas com BCrypt
- Controle de acesso baseado em roles

### Visualização e Busca
- Mapa interativo com marcadores de pets perdidos
- Filtros inteligentes:
  - Tipo de animal (cachorro, gato, outros)
  - Bairro/localização
  - Data de avistamento
  - Status (ativo/resolvido)
- Busca por proximidade geográfica

### Administração
- Painel admin para gerenciar todos os relatórios
- Moderação de conteúdo
- Estatísticas regionais de relatórios

---

## Screenshots

![Tela de Login e Registro](imagens/1.png)

*Autenticação segura com JWT, permitindo novo registro de usuário e login para acessar o sistema.*

---

![Mapa de Relatórios](imagens/2.png)

*Visualização interativa de todos os relatórios de pets no mapa com marcadores georreferenciados.*

---

![Formulário de Criação de Relatório](imagens/3.png)

*Interface intuitiva para criar relatórios com upload de imagem, descrição, tipo de animal e localização GPS.*

---

![Detalhes do Relatório](imagens/4.png)

*Visualização completa do relatório com imagem, informações do pet, localização no mapa e opções de resolução.*

---

## Arquitetura

### Arquitetura do Sistema

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

### Modelo de Dados (Diagrama ER)

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

**Relacionamentos:**
- Um **User** pode criar múltiplos **Reports** (1:N)
- Cada **Report** pertence a um **User**
- **Resolved** indica se o pet foi encontrado/resgatado

### Fluxo de Criação de Relatório

```mermaid
sequenceDiagram
    actor User
    participant Frontend
    participant API
    participant Security as JWT Security
    participant DB as PostgreSQL
    participant Cloud as Cloudinary
    
    User->>Frontend: Preencher formulário<br/>(foto, descrição, localização)
    Frontend->>Frontend: Obter coordenadas GPS<br/>(navegador)
    
    Frontend->>+API: POST /api/reports<br/>Authorization: Bearer {token}
    API->>Security: Validar token JWT
    Security-->>API: Token válido (userId)
    
    alt Upload de Imagem
        API->>+Cloud: Upload imagem
        Cloud-->>-API: URL da imagem
    end
    
    API->>API: Criar objeto Report<br/>{type, desc, img_url, lat, lng}
    API->>DB: Salvar relatório
    DB-->>API: Relatório criado (id, timestamp)
    
    API-->>-Frontend: 201 Created<br/>{id, imageUrl, location}
    Frontend->>Frontend: Adicionar marcador no mapa
    Frontend-->>User: Relatório criado com sucesso
```

---

## Tecnologias

### Backend

| Tecnologia | Versão | Propósito |
|------------|--------|-----------|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.2.3 | Framework web |
| Spring Security | 6.x | Autenticação JWT |
| Spring Data JPA | 3.x | Persistência ORM |
| Hibernate | 6.x | Implementação ORM |
| PostgreSQL | 14 | Banco de dados relacional |
| Lombok | 1.18.x | Redução de boilerplate |
| Maven | 3.9.6 | Build e dependências |

### Serviços Externos

| Serviço | Propósito |
|---------|-----------|
| Cloudinary | Armazenamento de imagens e CDN |
| Swagger/OpenAPI | Documentação interativa da API |
| Docker | Containerização e deployment |

### Segurança

- JWT (JSON Web Tokens) para autenticação stateless
- BCrypt para hash de senhas
- CORS configurado para origens permitidas
- Controle de Acesso Baseado em Roles (USER/ADMIN)

---

## Uso

### Fluxo Completo de Exemplo

#### 1. Registrar usuário

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

**Resposta:**
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

#### 3. Criar relatório (com imagem)

```bash
curl -X POST http://localhost:8080/api/reports \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -F "animalType=DOG" \
  -F "description=Cachorro vira-lata, pelagem marrom, perdido perto do parque" \
  -F "latitude=-28.1234" \
  -F "longitude=-54.9876" \
  -F "neighborhood=Centro" \
  -F "image=@/caminho/para/foto-cachorro.jpg"
```

**Resposta:**
```json
{
  "id": "b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc",
  "userId": "a7b3c1d2-e5f6-4a9b-8c7d-1234567890ab",
  "animalType": "DOG",
  "description": "Cachorro vira-lata, pelagem marrom, perdido perto do parque",
  "imageUrl": "https://res.cloudinary.com/yourcloud/image/upload/v123456/pets/abc123.jpg",
  "latitude": -28.1234,
  "longitude": -54.9876,
  "neighborhood": "Centro",
  "reportDate": "2025-12-18T23:30:00Z",
  "resolved": false,
  "createdAt": "2025-12-18T23:30:15Z"
}
```

#### 4. Buscar relatórios com filtros

```bash
# Buscar cachorros não resolvidos no bairro Centro
curl -X GET "http://localhost:8080/api/reports/search?type=DOG&neighborhood=Centro&resolved=false" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

#### 5. Marcar relatório como resolvido

```bash
curl -X PATCH http://localhost:8080/api/reports/b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc/resolve \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**Resposta:**
```json
{
  "id": "b8c4d2e3-f6a7-5b0c-9d8e-2345678901bc",
  "resolved": true,
  "resolvedAt": "2025-12-19T10:15:00Z",
  "message": "Pet encontrado! Obrigado por ajudar a comunidade."
}
```

---

## API Endpoints

### Autenticação

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| `POST` | `/api/auth/register` | Registrar novo usuário | Não |
| `POST` | `/api/auth/login` | Login e obter JWT | Não |

### Relatórios

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| `POST` | `/api/reports` | Criar relatório com imagem | USER |
| `GET` | `/api/reports` | Listar todos os relatórios | USER |
| `GET` | `/api/reports/{id}` | Buscar relatório por ID | USER |
| `GET` | `/api/reports/search` | Buscar com filtros avançados | USER |
| `PATCH` | `/api/reports/{id}/resolve` | Marcar como resolvido | USER (dono) |
| `PUT` | `/api/reports/{id}` | Atualizar relatório | USER (dono) |
| `DELETE` | `/api/reports/{id}` | Deletar relatório | USER (dono) ou ADMIN |

### Parâmetros de Busca (`/api/reports/search`)

| Parâmetro | Tipo | Valores | Descrição |
|-----------|------|---------|-----------|
| `type` | string | `DOG`, `CAT`, `BIRD`, `OTHER` | Tipo de animal |
| `neighborhood` | string | Texto livre | Bairro/localização |
| `resolved` | boolean | `true`, `false` | Status de resolução |
| `startDate` | date | ISO 8601 | Data inicial |
| `endDate` | date | ISO 8601 | Data final |
| `latitude` | decimal | -90 a 90 | Latitude para busca por proximidade |
| `longitude` | decimal | -180 a 180 | Longitude para busca por proximidade |
| `radius` | integer | km | Raio de busca (combinado com lat/lng) |

### Documentação Completa

Acesse a documentação interativa Swagger:
- **Local:** `http://localhost:8080/swagger-ui.html`
- **Produção:** `https://your-api.com/swagger-ui.html`

---
