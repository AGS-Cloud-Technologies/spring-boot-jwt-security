# JWT Authentication Example - Spring Boot

A production-ready JWT (JSON Web Token) authentication system built with Spring Boot 4.0.2 and Java 25. This project demonstrates best practices for implementing secure token-based authentication.

## Features

- **JWT Token Generation & Validation**: Industry-standard JJWT library (v0.12.3)
- **Spring Security Integration**: Configured with stateless authentication
- **User Registration & Login**: Complete authentication flow
- **Role-Based Access Control**: RBAC implementation with Spring Security
- **Password Encryption**: BCrypt password hashing
- **Database Integration**: JPA with H2 (easily replaceable with PostgreSQL/MySQL)
- **Input Validation**: Jakarta validation with custom constraints
- **Exception Handling**: Centralized JWT error handling
- **CORS Support**: Pre-configured for cross-origin requests
- **Production Ready**: Security best practices implemented

## Technology Stack

- **Java**: 25 LTS
- **Spring Boot**: 4.0.2
- **Spring Security**: 6.x
- **JWT Library**: JJWT 0.12.3
- **Database**: H2 (In-memory for development)
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven
- **Lombok**: For reducing boilerplate

## Project Structure

```
src/main/java/com/jwt/example/JwtExample/
├── config/
│   └── SecurityConfig.java          # Security configuration
├── controller/
│   ├── AuthController.java          # Authentication endpoints
│   ├── UserController.java          # Protected user endpoints
│   └── PublicController.java        # Public endpoints
├── dto/
│   ├── LoginRequest.java            # Login request DTO
│   ├── SignUpRequest.java           # Registration request DTO
│   ├── JwtResponse.java             # JWT response DTO
│   └── ApiResponse.java             # Generic API response
├── entity/
│   └── User.java                    # User entity
├── repository/
│   └── UserRepository.java          # User data access
├── security/
│   ├── JwtTokenProvider.java        # JWT token operations
│   ├── JwtAuthenticationFilter.java # JWT request filter
│   └── JwtAuthenticationEntryPoint.java # Exception handler
├── service/
│   ├── CustomUserDetailsService.java # User details loading
│   └── UserService.java             # User business logic
└── JwtExampleApplication.java       # Main application class
```

## Getting Started

### Prerequisites

- Java 25 JDK
- Maven 3.8+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation

1. **Clone/Download the project**
   ```bash
   cd E:\Spring_Boot\JwtExample
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

## API Endpoints

### Public Endpoints (No Authentication Required)

#### 1. Health Check
```
GET /api/public/health
Response: {"success": true, "message": "Application is running"}
```

#### 2. Application Info
```
GET /api/public/info
Response: {"success": true, "message": "JWT Authentication Example - Production Ready", "data": "Version: 1.0.0"}
```

### Authentication Endpoints

#### 1. User Registration
```
POST /api/auth/signup
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "password": "secure_password123"
}

Response (201 Created):
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "enabled": true,
    "createdAt": 1708111200000,
    "updatedAt": 1708111200000
  }
}
```

#### 2. User Login
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "secure_password123"
}

Response (200 OK):
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcwODExMTIwMCwiZXhwIjoxNzA4MTk3NjAwfQ...",
    "type": "Bearer",
    "username": "john_doe",
    "email": "john@example.com",
    "expiresIn": 86400000
  }
}
```

#### 3. Validate Token
```
GET /api/auth/validate
Authorization: Bearer <YOUR_JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Token is valid",
  "data": "john_doe"
}
```

### Protected Endpoints (Authentication Required)

#### 1. Get User Profile
```
GET /api/user/profile
Authorization: Bearer <YOUR_JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "User profile retrieved",
  "data": "john_doe"
}
```

#### 2. Get User Info
```
GET /api/user/info
Authorization: Bearer <YOUR_JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "User info retrieved",
  "data": {
    "username": "john_doe",
    "authorities": "[ROLE_USER]"
  }
}
```

## Configuration

### application.properties

Key configuration properties:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:testdb

# JWT
jwt.secret=mySecretKeyForJWTTokenGenerationAndValidationPurposeOnly
jwt.expiration=86400000  # 24 hours in milliseconds
```

### Security Configuration

The `SecurityConfig` class defines:
- CSRF disabled (suitable for stateless REST APIs)
- Stateless session management
- JWT authentication filter
- Public and protected endpoint mappings
- BCrypt password encoding

## Key Components

### 1. JwtTokenProvider
Handles JWT token generation and validation:
- **generateToken()**: Creates JWT from username
- **validateToken()**: Validates token signature and expiration
- **getUsernameFromToken()**: Extracts username from token

### 2. JwtAuthenticationFilter
Filter that:
- Extracts JWT from Authorization header
- Validates the token
- Sets authentication in SecurityContext

### 3. SecurityConfig
Spring Security configuration that:
- Configures HTTP security rules
- Integrates JWT filter
- Defines public/protected routes
- Sets up password encoding

### 4. UserService & CustomUserDetailsService
- User registration and management
- Integration with Spring Security's UserDetailsService

## Authentication Flow

1. User sends credentials to `/api/auth/login`
2. AuthController authenticates using AuthenticationManager
3. If valid, JwtTokenProvider generates JWT token
4. Token is returned to client
5. Client includes token in Authorization header: `Bearer <token>`
6. JwtAuthenticationFilter validates token on each request
7. User is authenticated and can access protected resources

## Error Handling

### Authentication Errors
```
401 Unauthorized: Invalid credentials
Response: {"success": false, "message": "Invalid username or password"}
```

### Validation Errors
```
400 Bad Request: Invalid input
Response: {"success": false, "message": "Username is required"}
```

### Token Errors
```
401 Unauthorized: Invalid/Expired token
Response: {"error": "Unauthorized", "message": "JWT token expired"}
```

## Database

### H2 Database Console
Access at: `http://localhost:8080/h2-console`

Default credentials:
- URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (empty)

### User Table
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  enabled BOOLEAN NOT NULL,
  created_at BIGINT NOT NULL,
  updated_at BIGINT
);
```

## Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
Use Postman or cURL to test endpoints.

Example cURL requests:

**Register**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "fullName": "Test User",
    "password": "test123456"
  }'
```

**Login**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "test123456"
  }'
```

**Access Protected Endpoint**
```bash
curl -X GET http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Security Best Practices Implemented

✅ **JWT Signing**: Uses HS512 algorithm with strong secret key
✅ **Password Hashing**: BCrypt with salt
✅ **Stateless Authentication**: No session storage
✅ **CORS Configuration**: Controlled cross-origin access
✅ **Input Validation**: Jakarta validation annotations
✅ **Exception Handling**: Proper error responses
✅ **Token Expiration**: Configurable token lifetime
✅ **Authorization**: Role-based access control

## Production Considerations

### Before Deploying to Production

1. **Change JWT Secret**
   - Generate a strong, random secret key (at least 32 bytes)
   - Store in environment variables or secure vault (AWS Secrets Manager, HashiCorp Vault)

2. **Database**
   - Replace H2 with production database (PostgreSQL, MySQL)
   - Configure proper connection pooling
   - Enable SSL for database connections

3. **HTTPS**
   - Enable SSL/TLS for API
   - Use valid SSL certificates

4. **Token Expiration**
   - Adjust `jwt.expiration` based on security requirements
   - Implement refresh token mechanism

5. **Logging**
   - Use proper logging framework (SLF4J)
   - Log security events
   - Do NOT log sensitive information (passwords, tokens)

6. **Rate Limiting**
   - Implement rate limiting on authentication endpoints
   - Use Spring Cloud Circuit Breaker

7. **Monitoring**
   - Add Spring Boot Actuator
   - Monitor authentication failures
   - Set up alerts for suspicious activities

8. **API Documentation**
   - Add Springdoc OpenAPI (Swagger) for API documentation

## Extending the Project

### Add Refresh Token
Implement refresh token mechanism in JwtTokenProvider:
```java
public String generateRefreshToken(String username) {
    // Implementation
}
```

### Add Role-Based Access Control
Extend User entity with roles and authorities.

### Add Email Verification
Send verification email during registration.

### Add OAuth2 Integration
Integrate with OAuth2 providers (Google, GitHub).

## Troubleshooting

### Issue: "Unauthorized" on protected endpoints
- Ensure token is included in Authorization header
- Format: `Authorization: Bearer <token>`
- Check if token has expired

### Issue: Token validation fails
- Check JWT secret key matches between token generation and validation
- Verify token hasn't been tampered with

### Issue: User not found after registration
- Check H2 console if user was actually saved
- Verify database connection

## License

MIT License

## Support

For issues and questions, create an issue in the repository or contact the development team.

## Changelog

### v1.0.0 (Initial Release)
- JWT authentication with JJWT 0.12.3
- User registration and login
- Role-based access control
- JWT token validation
- Spring Security integration
- Production-ready configuration

