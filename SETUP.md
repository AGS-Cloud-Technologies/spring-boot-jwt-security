# JWT Spring Boot Setup Guide

## Quick Start

### Step 1: Prerequisites
- Java 25 JDK
- Maven 3.8+
- Git
- IDE (IntelliJ IDEA recommended)

### Step 2: Project Setup

```bash
cd E:\Spring_Boot\JwtExample
mvn clean install
```

### Step 3: Run the Application

```bash
mvn spring-boot:run
```

Server will start on `http://localhost:8080`

## Default Credentials (for testing)

The application includes seed data. You can test with:
- Username: `testuser`
- Password: `TestPassword123`

## API Testing

### Using cURL

#### 1. Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "fullName": "New User",
    "password": "SecurePass123"
  }'
```

#### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "SecurePass123"
  }'
```

Response:
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXd1c2VyIiwiaWF0IjoxNzA4MTExMjAwLCJleHAiOjE3MDgxOTc2MDB9.xxx",
    "type": "Bearer",
    "username": "newuser",
    "email": "newuser@example.com",
    "expiresIn": 86400000
  }
}
```

#### 3. Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXd1c2VyIiwiaWF0IjoxNzA4MTExMjAwLCJleHAiOjE3MDgxOTc2MDB9.xxx"
```

### Using Postman

1. **Create Environment Variable**
   - Variable name: `token`
   - Initial value: (empty)

2. **Register User**
   - Method: POST
   - URL: `{{base_url}}/api/auth/signup`
   - Body (JSON):
   ```json
   {
     "username": "postmanuser",
     "email": "postman@example.com",
     "fullName": "Postman User",
     "password": "PostmanPass123"
   }
   ```

3. **Login and Save Token**
   - Method: POST
   - URL: `{{base_url}}/api/auth/login`
   - Body (JSON):
   ```json
   {
     "username": "postmanuser",
     "password": "PostmanPass123"
   }
   ```
   - In Tests tab, add:
   ```javascript
   if (pm.response.code === 200) {
       pm.environment.set("token", pm.response.json().data.token);
   }
   ```

4. **Access Protected Endpoint**
   - Method: GET
   - URL: `{{base_url}}/api/user/profile`
   - Headers:
     - Key: `Authorization`
     - Value: `Bearer {{token}}`

## File Structure

```
JwtExample/
├── src/main/java/com/jwt/example/JwtExample/
│   ├── config/
│   │   ├── SecurityConfig.java        # Security configuration
│   │   └── CorsConfig.java            # CORS configuration
│   ├── controller/
│   │   ├── AuthController.java        # Authentication endpoints
│   │   ├── UserController.java        # User endpoints
│   │   └── PublicController.java      # Public endpoints
│   ├── dto/
│   │   ├── LoginRequest.java
│   │   ├── SignUpRequest.java
│   │   ├── JwtResponse.java
│   │   ├── ApiResponse.java
│   │   └── UserDTO.java
│   ├── entity/
│   │   └── User.java
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── InvalidTokenException.java
│   │   └── TokenExpiredException.java
│   ├── mapper/
│   │   └── UserMapper.java
│   ├── repository/
│   │   └── UserRepository.java
│   ├── security/
│   │   ├── JwtTokenProvider.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtAuthenticationEntryPoint.java
│   ├── service/
│   │   ├── UserService.java
│   │   └── CustomUserDetailsService.java
│   ├── util/
│   │   ├── ValidationUtil.java
│   │   └── JwtSecretGenerator.java
│   └── JwtExampleApplication.java
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   └── application-prod.properties
├── src/test/java/
│   └── com/jwt/example/JwtExample/
│       ├── controller/
│       │   └── AuthControllerIntegrationTest.java
│       └── security/
│           └── JwtTokenProviderTest.java
├── pom.xml
├── README.md
└── SETUP.md
```

## Configuration

### application.properties
Main configuration file with development defaults.

### application-dev.properties
Development-specific configuration:
- H2 in-memory database
- SQL logging enabled
- Debug logging

### application-prod.properties
Production-ready configuration:
- Requires external database
- Minimal logging
- Security hardened

## Running Different Profiles

### Development
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Production
```bash
export JWT_SECRET="your-production-secret-key"
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

## Database Access

### H2 Console (Development)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Security Configuration

### Password Requirements
- Minimum 6 characters (can be customized)
- No special format requirements (can enforce stronger passwords)

### JWT Token
- Algorithm: HS512
- Expiration: 24 hours (configurable)
- Payload contains username as subject

### CORS
- All origins allowed (`*`) in dev
- Should be restricted in production

## Troubleshooting

### Issue: Port 8080 already in use
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Issue: Maven build fails
```bash
# Clear Maven cache
mvn clean install -U
```

### Issue: Cannot find h2 console
- Ensure H2 dependency is in pom.xml
- Check application.properties for `spring.h2.console.enabled=true`

### Issue: Token validation fails
- Ensure jwt.secret is the same in token generation and validation
- Check token hasn't expired
- Verify token format in Authorization header (Bearer prefix)

## Production Deployment

### 1. Generate Production JWT Secret
```bash
java -cp target/classes com.jwt.example.JwtExample.util.JwtSecretGenerator
```

### 2. Set Environment Variables
```bash
export JWT_SECRET="generated-secret-key"
export DB_PASSWORD="database-password"
```

### 3. Build Production JAR
```bash
mvn clean package -P production
```

### 4. Run Production JAR
```bash
java -jar target/JwtExample-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## Monitoring

### Health Check
```bash
curl http://localhost:8080/api/public/health
```

### Application Info
```bash
curl http://localhost:8080/api/public/info
```

## Common Issues and Solutions

| Issue | Solution |
|-------|----------|
| 401 Unauthorized | Verify token in Authorization header |
| 403 Forbidden | User may not have required roles |
| 500 Internal Error | Check application logs and database connection |
| Invalid JWT | Regenerate token or check secret key |
| CORS Error | Check CORS configuration and allowed origins |

## Next Steps

1. Customize JWT expiration time
2. Add refresh token mechanism
3. Integrate with external database
4. Add email verification
5. Implement OAuth2 integration
6. Add API documentation (Swagger)
7. Deploy to cloud (AWS, Azure, GCP)

