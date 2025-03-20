# Blog API

Modern blog API built with Java & Spring Boot. Features JWT auth, Redis caching, Google OAuth2, and Minio storage.
## ✨ Key Features

### **Core Functionality**
- **Caching**: Efficient Redis caching for improved performance.
- **MinIO Storage**: Scalable object storage for media files (e.g., images).
- **Role-Based Access Control (RBAC)**: Secure access with `ADMIN` and `USER` roles.
- **Google OAuth2**: Seamless login and authentication via Google accounts.
- **HATEOAS (Pagination)**: RESTful navigation with paginated responses.
- **JWT Authentication**: Secure token-based authentication for API access.

### **Content Management**
- **CRUD for Categories**: Create, Read, Update, and Delete post categories.
- **CRUD for Posts**: Full management of blog posts.
- **CRUD for Comments & Replies**: Manage comments and nested replies.
- **Likes & Dislikes**: Users can like or dislike posts and comments.

### **Modern API Design**
- Scalable, secure, and developer-friendly architecture.
- Built with Java and Spring Boot for robustness and flexibility.

![Tech Stack](https://img.shields.io/badge/Spring_Boot-3.1.0-green?logo=spring)
![Database](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Cache](https://img.shields.io/badge/Redis-7.0-red?logo=redis)

---

## ⚙️ Configuration Setup

### 1. Database & Services
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=DB_USER
spring.datasource.password=DB_PASS

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379

# MinIO
minio.url=http://localhost:9000
minio.accessKey=MINIO_USER
minio.secretKey=MINIO_PASSWORD
```

**Note: Crate the MySQL database and insert the username and password, also insert the Minio credentials**

### 2. Security Config
Generate JWT key with Node.js
```bash
node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
```
. Configure your Google project in google cloud console

. Generate your Google client id and client secret key in the oauth2 client tab
```properties
# JWT Secret (generate with Node.js)
security.jwt.secret=your_32_char_hex_string

# Google OAuth
spring.security.oauth2.client.registration.google.client-id=CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=CLIENT_SECRET
```

## 🐳 Docker Services
# Minio Storage
```bash
docker run -p 9000:9000 -p 9001:9001 \
  -v ~/minio/data:/data \
  -e "MINIO_ROOT_USER=admin" \
  -e "MINIO_ROOT_PASSWORD=securepassword" \
  quay.io/minio/minio server /data --console-address ":9001"
```
# Redis Cache
```bash
docker run --name blog-redis -p 6379:6379 -d redis:alpine
```

## 🔑 First-Time Admin Setup
1. Start application

2. Execute in MySQL:
````sql
INSERT INTO users_tb(user_id, email, username, role, created_at)
VALUES (UUID_TO_BIN(UUID()), 'admin@email.com', 'AdminName', 'ADMIN', NOW());
````
**Note: Your e-mail and username must match the one you will use when logging with Google**

## 🔌 API Testing Workflow

1. Access `http://localhost:8080`.

2. Open the browser's **Inspect Tool** (F12) and navigate to the **Network** tab.

3. Log in using your Google admin email.

4. After logging in, look for the request to `/api/posts/search?page=0&direction=ASC` in the **Network** tab.

5. In the response headers, locate the `AuthToken` cookie.

6. Copy the `AuthToken` value and set it as the `auth_token` variable in your Postman collection.

Now you're ready to test the API endpoints in Postman!

