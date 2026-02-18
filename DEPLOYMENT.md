# Production Deployment Guide

## Pre-Deployment Checklist

- [ ] All tests passing
- [ ] JWT secret key generated and stored securely
- [ ] Database configured and tested
- [ ] HTTPS/SSL certificate obtained
- [ ] Environment variables configured
- [ ] Logging properly configured
- [ ] Monitoring and alerting set up
- [ ] Backup and recovery plan established

## Security Hardening

### 1. JWT Secret Management

**Never hardcode secrets in code!**

#### Generate Secure Secret
```bash
java -cp target/classes com.jwt.example.JwtExample.util.JwtSecretGenerator
```

#### Store in Environment Variable
```bash
export JWT_SECRET="base64-encoded-secret-key"
```

Or use AWS Secrets Manager:
```bash
aws secretsmanager create-secret --name jwt-secret --secret-string "base64-encoded-secret-key"
```

### 2. Database Security

#### PostgreSQL (Recommended)
```sql
CREATE USER jwtuser WITH ENCRYPTED PASSWORD 'strong_password';
CREATE DATABASE jwtdb OWNER jwtuser;
GRANT CONNECT ON DATABASE jwtdb TO jwtuser;
GRANT USAGE ON SCHEMA public TO jwtuser;
GRANT CREATE ON SCHEMA public TO jwtuser;
```

#### MySQL
```sql
CREATE USER 'jwtuser'@'localhost' IDENTIFIED BY 'strong_password';
CREATE DATABASE jwtdb;
GRANT ALL PRIVILEGES ON jwtdb.* TO 'jwtuser'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Application Configuration

#### application-prod.properties
```properties
# Database
spring.datasource.url=jdbc:postgresql://db.example.com:5432/jwtdb
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.hikari.maximum-pool-size=10

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=3600000  # 1 hour for production

# Security
server.ssl.enabled=true
server.ssl.key-store=${SSL_KEYSTORE_PATH}
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}

# Logging
logging.level.root=WARN
logging.level.com.jwt.example=INFO
```

## Docker Deployment

### Dockerfile
```dockerfile
FROM openjdk:25-slim

WORKDIR /app

COPY target/JwtExample-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Build & Run
```bash
# Build
docker build -t jwt-example:1.0 .

# Run
docker run -e JWT_SECRET="secret-key" \
           -e DB_PASSWORD="db-password" \
           -p 8080:8080 \
           jwt-example:1.0
```

## Kubernetes Deployment

### deployment.yaml
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: jwt-example
spec:
  replicas: 3
  selector:
    matchLabels:
      app: jwt-example
  template:
    metadata:
      labels:
        app: jwt-example
    spec:
      containers:
      - name: jwt-example
        image: jwt-example:1.0
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: JWT_SECRET
          valueFrom:
            secretKeyRef:
              name: jwt-secrets
              key: jwt-secret
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: db-secrets
              key: password
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
```

### Service
```yaml
apiVersion: v1
kind: Service
metadata:
  name: jwt-example-service
spec:
  type: LoadBalancer
  ports:
  - port: 80
    targetPort: 8080
  selector:
    app: jwt-example
```

## AWS Deployment

### Using Elastic Beanstalk

1. **Package application**
```bash
mvn clean package
```

2. **Create `.ebextensions/` config**
```yaml
# .ebextensions/01_environment.config
option_settings:
  aws:autoscaling:asg:
    MinSize: 2
    MaxSize: 4
  aws:ec2:instances:
    InstanceTypes: t3.medium
```

3. **Deploy**
```bash
eb init -p java-25 jwt-example
eb create jwt-example-env
eb deploy
```

### Using RDS for Database
```bash
# Create RDS instance
aws rds create-db-instance \
  --db-instance-identifier jwt-db \
  --db-instance-class db.t3.micro \
  --engine postgres \
  --master-username jwtuser \
  --master-user-password 'strong_password'
```

## Monitoring & Logging

### CloudWatch Logging
```properties
# application-prod.properties
spring.cloud.aws.logs.enabled=true
spring.cloud.aws.logs.log-group=/aws/elasticbeanstalk/jwt-example
```

### Application Metrics
```properties
management.endpoints.web.exposure.include=health,metrics,prometheus
management.metrics.export.prometheus.enabled=true
```

### New Relic Integration
```properties
# Add New Relic Java agent
-javaagent:/path/to/newrelic/newrelic.jar
```

## SSL/TLS Configuration

### Generate Self-Signed Certificate (Testing only)
```bash
keytool -genkey -alias tomcat \
  -storetype PKCS12 \
  -keyalg RSA \
  -keysize 2048 \
  -keystore keystore.p12 \
  -validity 3650
```

### Production Certificate (Let's Encrypt)
```bash
certbot certonly --standalone -d api.example.com
```

### Application Configuration
```properties
server.ssl.key-store=/path/to/keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
server.port=8443
```

## Load Balancing

### Nginx Configuration
```nginx
upstream jwt_app {
    server localhost:8080;
    server localhost:8081;
    server localhost:8082;
}

server {
    listen 443 ssl http2;
    server_name api.example.com;

    ssl_certificate /path/to/certificate.crt;
    ssl_certificate_key /path/to/private.key;

    location / {
        proxy_pass http://jwt_app;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

## Database Backup Strategy

### PostgreSQL Backup
```bash
# Full backup
pg_dump -h localhost -U jwtuser jwtdb > backup.sql

# Point-in-time recovery
pg_dump -Fc jwtdb > backup.dump
pg_restore -d jwtdb backup.dump
```

### Automated Backup
```bash
#!/bin/bash
# backup.sh
BACKUP_DIR="/backups"
DATE=$(date +%Y%m%d_%H%M%S)
pg_dump -h localhost -U jwtuser jwtdb > $BACKUP_DIR/backup_$DATE.sql
```

## Performance Optimization

### Connection Pool Tuning
```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
```

### Caching
```properties
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

## Rate Limiting

```java
@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    private final RateLimiter rateLimiter = RateLimiter.create(100); // 100 requests per second

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        if (!rateLimiter.tryAcquire()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
```

## CI/CD Pipeline

### GitHub Actions Example
```yaml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 25
      uses: actions/setup-java@v2
      with:
        java-version: '25'
    
    - name: Build with Maven
      run: mvn clean package
    
    - name: Deploy to AWS
      env:
        AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}
        AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
      run: |
        aws s3 cp target/*.jar s3://my-bucket/
        aws elasticbeanstalk create-application-version ...
```

## Incident Response

### Monitor Key Metrics
- Authentication failure rate
- Token validation errors
- Database connection pool usage
- Response time percentiles
- Error rates

### Common Issues & Remediation

**Issue: High authentication failure rate**
```bash
# Check logs
tail -f /var/log/jwt-example/application.log

# Restart service
systemctl restart jwt-example
```

**Issue: Database connection errors**
```bash
# Check database status
systemctl status postgresql

# Check connection pool
curl http://localhost:8080/actuator/metrics/hikaricp.connections
```

## Rollback Procedure

```bash
# Keep previous versions
aws s3 cp s3://my-bucket/JwtExample-0.0.1.jar ./
java -jar JwtExample-0.0.1.jar

# Or with Docker
docker pull jwt-example:0.0.1
docker run -d jwt-example:0.0.1
```

## Post-Deployment Verification

- [ ] Health check endpoint responding
- [ ] Login functionality working
- [ ] Protected endpoints require authentication
- [ ] Token validation working
- [ ] Database connections stable
- [ ] Logs capturing correctly
- [ ] Monitoring dashboards displaying data
- [ ] Backups running successfully

