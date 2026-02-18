# JWT Spring Boot Example - File Index

## Complete File Structure

```
E:\Spring_Boot\JwtExample/
│
├── src/
│   ├── main/
│   │   ├── java/com/jwt/example/JwtExample/
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java              [Security configuration]
│   │   │   │   └── CorsConfig.java                  [CORS configuration]
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java              [/api/auth endpoints]
│   │   │   │   ├── UserController.java              [/api/user endpoints]
│   │   │   │   └── PublicController.java            [/api/public endpoints]
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── SignUpRequest.java               [Registration request]
│   │   │   │   ├── LoginRequest.java                [Login request]
│   │   │   │   ├── JwtResponse.java                 [Token response]
│   │   │   │   ├── ApiResponse.java                 [Generic response wrapper]
│   │   │   │   └── UserDTO.java                     [User data transfer]
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   └── User.java                        [JPA User entity]
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java      [Exception handler]
│   │   │   │   ├── TokenExpiredException.java       [Expired token error]
│   │   │   │   └── InvalidTokenException.java       [Invalid token error]
│   │   │   │
│   │   │   ├── mapper/
│   │   │   │   └── UserMapper.java                  [Entity-DTO conversion]
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java              [User data access]
│   │   │   │
│   │   │   ├── security/
│   │   │   │   ├── JwtTokenProvider.java            [JWT operations]
│   │   │   │   ├── JwtAuthenticationFilter.java     [Request filter]
│   │   │   │   └── JwtAuthenticationEntryPoint.java [Error handler]
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── UserService.java                 [User business logic]
│   │   │   │   └── CustomUserDetailsService.java    [Spring Security integration]
│   │   │   │
│   │   │   ├── util/
│   │   │   │   ├── ValidationUtil.java              [Input validation]
│   │   │   │   └── JwtSecretGenerator.java          [Secret key generation]
│   │   │   │
│   │   │   └── JwtExampleApplication.java           [Main application class]
│   │   │
│   │   └── resources/
│   │       ├── application.properties               [Main configuration]
│   │       ├── application-dev.properties           [Development profile]
│   │       └── application-prod.properties          [Production profile]
│   │
│   └── test/
│       └── java/com/jwt/example/JwtExample/
│           ├── controller/
│           │   └── AuthControllerIntegrationTest.java
│           └── security/
│               └── JwtTokenProviderTest.java
│
├── Documentation Files:
│   ├── README.md                    [Complete documentation]
│   ├── SETUP.md                     [Setup guide]
│   ├── DEPLOYMENT.md                [Production deployment]
│   ├── QUICKSTART.md                [Quick reference]
│   ├── PROJECT_SUMMARY.md           [Project overview]
│   └── FILE_INDEX.md                [This file]
│
├── Configuration Files:
│   ├── pom.xml                      [Maven dependencies]
│   ├── .gitignore                   [Git ignore rules]
│   └── JwtExample.iml               [IntelliJ project file]
│
└── Build Files:
    ├── mvnw                         [Maven wrapper (Linux/Mac)]
    ├── mvnw.cmd                     [Maven wrapper (Windows)]
    └── target/                      [Build output]
```

## Quick Navigation

### By Purpose

**API & Controllers**
- `controller/AuthController.java` - Authentication endpoints
- `controller/UserController.java` - User endpoints
- `controller/PublicController.java` - Public endpoints

**Security**
- `security/JwtTokenProvider.java` - JWT token logic
- `security/JwtAuthenticationFilter.java` - Token validation
- `security/JwtAuthenticationEntryPoint.java` - Error responses
- `config/SecurityConfig.java` - Security setup

**Database**
- `entity/User.java` - User entity
- `repository/UserRepository.java` - Data access
- `service/UserService.java` - User operations

**Data Transfer**
- `dto/SignUpRequest.java` - Registration input
- `dto/LoginRequest.java` - Login input
- `dto/JwtResponse.java` - Token response
- `dto/ApiResponse.java` - Response wrapper
- `dto/UserDTO.java` - User DTO

**Utilities**
- `util/ValidationUtil.java` - Validation helpers
- `util/JwtSecretGenerator.java` - Key generation
- `mapper/UserMapper.java` - Entity conversion

**Exception Handling**
- `exception/GlobalExceptionHandler.java` - Central handler
- `exception/TokenExpiredException.java` - Token error
- `exception/InvalidTokenException.java` - Invalid error

**Testing**
- `test/controller/AuthControllerIntegrationTest.java`
- `test/security/JwtTokenProviderTest.java`

**Configuration**
- `resources/application.properties` - Main config
- `resources/application-dev.properties` - Dev config
- `resources/application-prod.properties` - Prod config

### By Usage

**Authentication Flow**
1. `AuthController.signup()` - Register user
2. `AuthController.login()` - Authenticate
3. `JwtTokenProvider.generateToken()` - Create JWT
4. Client sends: `Authorization: Bearer <token>`
5. `JwtAuthenticationFilter` - Validates token
6. `SecurityConfig` - Protects endpoints

**Database Operations**
1. `UserService.createUser()` - Register
2. `UserRepository.findByUsername()` - Query
3. `User` entity persisted to H2

**Error Handling**
1. `GlobalExceptionHandler` catches all exceptions
2. Returns proper HTTP status codes
3. Wraps response in `ApiResponse` DTO

## File Statistics

| Category | Count |
|----------|-------|
| Java Classes | 24 |
| Configuration Files | 3 |
| Documentation Files | 5 |
| Test Files | 2 |
| Total Source Files | 34 |

## Java Files by Layer

**Configuration (2)**
- SecurityConfig.java
- CorsConfig.java

**Controllers (3)**
- AuthController.java
- UserController.java
- PublicController.java

**Services (2)**
- UserService.java
- CustomUserDetailsService.java

**Security (3)**
- JwtTokenProvider.java
- JwtAuthenticationFilter.java
- JwtAuthenticationEntryPoint.java

**DTOs (5)**
- SignUpRequest.java
- LoginRequest.java
- JwtResponse.java
- ApiResponse.java
- UserDTO.java

**Entities (1)**
- User.java

**Repositories (1)**
- UserRepository.java

**Mappers (1)**
- UserMapper.java

**Exceptions (3)**
- GlobalExceptionHandler.java
- TokenExpiredException.java
- InvalidTokenException.java

**Utilities (2)**
- ValidationUtil.java
- JwtSecretGenerator.java

**Main Application (1)**
- JwtExampleApplication.java

**Tests (2)**
- AuthControllerIntegrationTest.java
- JwtTokenProviderTest.java

## Documentation Files

| File | Purpose | Audience |
|------|---------|----------|
| README.md | Complete documentation | Everyone |
| SETUP.md | Installation & configuration | Developers |
| DEPLOYMENT.md | Production deployment | DevOps/Architects |
| QUICKSTART.md | 5-minute start guide | New users |
| PROJECT_SUMMARY.md | Overview of what was added | Project leads |
| FILE_INDEX.md | This file | Navigation reference |

## How to Use This File Index

1. **Finding a class?** Use "By Purpose" or "By Usage" sections
2. **Need to understand flow?** Check "By Usage" section
3. **Looking for configuration?** See "Configuration Files"
4. **Setting up project?** Start with SETUP.md or QUICKSTART.md
5. **Deploying to production?** Read DEPLOYMENT.md

## Getting Started

1. **Build**: `mvn clean install`
2. **Run**: `mvn spring-boot:run`
3. **Test**: `mvn test`
4. **Access**: `http://localhost:8080/api/public/health`

## Key Files to Know

**Must-Know Files**
- `pom.xml` - All dependencies
- `SecurityConfig.java` - How security works
- `AuthController.java` - Main entry points
- `JwtTokenProvider.java` - Token logic
- `application.properties` - Configuration

**Good to Know Files**
- `User.java` - Database schema
- `GlobalExceptionHandler.java` - Error handling
- `UserService.java` - Business logic

**Advanced Files**
- `application-prod.properties` - Production config
- `DEPLOYMENT.md` - Deployment guide
- `JwtSecretGenerator.java` - Key generation

---

Last Updated: February 17, 2026  
Java Version: 25 LTS  
Spring Boot: 4.0.2  
Status: ✅ Production Ready

