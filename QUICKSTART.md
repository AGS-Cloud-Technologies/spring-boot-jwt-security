# Quick Start Guide

## 1-Minute Setup

```bash
# Navigate to project
cd E:\Spring_Boot\JwtExample

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

Application runs on `http://localhost:8080`

## Test the API

### Register a User
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "demo",
    "email": "demo@test.com",
    "fullName": "Demo User",
    "password": "DemoPass123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "demo",
    "password": "DemoPass123"
  }'
```

Copy the `token` from the response.

### Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer <PASTE_TOKEN_HERE>"
```

## Project Features

✅ **JWT Authentication** - Industry-standard JJWT library  
✅ **Spring Security** - Complete security framework  
✅ **Database** - H2 in-memory with JPA  
✅ **Input Validation** - Jakarta validation annotations  
✅ **Exception Handling** - Global error handling  
✅ **CORS Support** - Pre-configured  
✅ **Unit Tests** - Integration & security tests  
✅ **Documentation** - Complete guides included  

## Project Files

### Core Files
- **SecurityConfig.java** - Security configuration
- **JwtTokenProvider.java** - JWT token operations
- **JwtAuthenticationFilter.java** - JWT request filter
- **UserService.java** - User business logic

### Controllers
- **AuthController.java** - /api/auth endpoints
- **UserController.java** - /api/user endpoints
- **PublicController.java** - /api/public endpoints

### Database
- **User.java** - User entity
- **UserRepository.java** - Data access

### DTOs
- **LoginRequest.java** - Login request
- **SignUpRequest.java** - Registration request
- **JwtResponse.java** - JWT response

## Available Endpoints

### Public (No Auth Required)
- `GET /api/public/health` - Health check
- `GET /api/public/info` - Application info
- `POST /api/auth/signup` - Register user
- `POST /api/auth/login` - Login user
- `GET /api/auth/validate` - Validate token

### Protected (Auth Required)
- `GET /api/user/profile` - Get profile
- `GET /api/user/info` - Get user info

## Default Configuration

- **Port**: 8080
- **Database**: H2 in-memory
- **JWT Expiration**: 24 hours
- **Profile**: dev (change in application.properties)

## File Structure

```
E:\Spring_Boot\JwtExample\
├── src/main/java/com/jwt/example/JwtExample/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/            # Data transfer objects
│   ├── entity/         # JPA entities
│   ├── exception/      # Exception handlers
│   ├── mapper/         # Object mappers
│   ├── repository/     # Data repositories
│   ├── security/       # Security classes
│   ├── service/        # Business logic
│   └── util/           # Utility classes
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   └── application-prod.properties
├── src/test/           # Unit & integration tests
├── pom.xml            # Maven dependencies
├── README.md          # Full documentation
├── SETUP.md           # Setup guide
├── DEPLOYMENT.md      # Deployment guide
└── .gitignore         # Git configuration
```

## Next Steps

1. **Read the README.md** for complete documentation
2. **Check SETUP.md** for detailed setup instructions
3. **Review DEPLOYMENT.md** for production deployment
4. **Customize the application**:
   - Change JWT secret key
   - Modify user roles and authorities
   - Add additional entities/repositories
   - Implement refresh tokens
   - Add email verification

## Production Checklist

Before deploying to production:

- [ ] Generate strong JWT secret key
- [ ] Configure external database (PostgreSQL/MySQL)
- [ ] Enable HTTPS/SSL
- [ ] Set up environment variables
- [ ] Configure logging
- [ ] Set up monitoring
- [ ] Implement rate limiting
- [ ] Test all endpoints
- [ ] Backup database strategy
- [ ] Security audit

## Troubleshooting

**Port already in use?**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

**Database errors?**
- Check H2 console: `http://localhost:8080/h2-console`
- Verify database connection in logs

**Token validation fails?**
- Ensure Authorization header format: `Bearer <token>`
- Check if token has expired
- Verify JWT secret key consistency

## Support & Resources

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Spring Security**: https://spring.io/projects/spring-security
- **JJWT**: https://github.com/jwtk/jjwt
- **Documentation**: See README.md

## Version Info

- **Java**: 25 LTS
- **Spring Boot**: 4.0.2
- **Spring Security**: 7.0.2
- **JJWT**: 0.12.3
- **Maven**: 3.8+

Good luck with your JWT implementation! 🚀

