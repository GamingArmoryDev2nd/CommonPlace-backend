# CommonPlace Backend

A Spring Boot backend with a MySQL database, packaged for Docker. This README explains how to build and run the app the same way you just did — using the provided Docker Compose setup.

> **TL;DR**
> 1) Build the backend image: `./gradlew bootBuildImage`  
> 2) Start everything: `docker compose up -d`  
> 3) Check logs: `docker compose logs -f backend`

---

## Prerequisites
- **Docker Desktop** (Compose v2; you should have the `docker compose` command)
- **JDK 21** *(only needed if you build with Gradle; the wrapper downloads Gradle automatically)*

## Project layout (typical)
```
my-project/
├─ build.gradle
├─ settings.gradle
├─ src/main/java/...  (your code)
├─ src/main/resources/application.properties
├─ Dockerfile (optional if you use buildpacks)
└─ docker-compose.yml
```

## Build the backend image
Use Spring Boot Buildpacks (no Dockerfile needed):

By default, the imageName is set in the `build.gradle` so you just need to execute:

```bash
./gradew bootBuildImage 
```

else execute the commands below 
```bash
./gradlew bootBuildImage --imageName=commonplace-backend:latest
```
or set the imageName in the `build.gradle`.

> Image names must be lowercase and may include `[a-z0-9._-]` only.

## Run with Docker Compose
The following compose file is known-good for this project. **Keep it exactly as-is** unless you know what you’re changing.

```yaml
services:
  db:
    image: mysql:latest
    environment:
      MYSQL_DATABASE: commonplace
      MYSQL_ROOT_PASSWORD: 1234
      MYSQL_USER: admin
      MYSQL_PASSWORD: admin1234
    healthcheck:
      test: ["CMD-SHELL","mysqladmin ping -h 127.0.0.1 -uroot -p1234 --silent"]
      interval: 5s
      timeout: 3s
      retries: 20

  backend:
    image: commonplace-backend:latest
    depends_on:
      db:
        condition: service_healthy
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/commonplace?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: admin1234
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8080:8080"
```

Start the stack:
```bash
docker compose up -d
```
Tail logs:
```bash
docker compose logs -f db
# in a second terminal
docker compose logs -f backend
```
Stop everything:
```bash
docker compose down
```
Reset the database (⚠️ deletes all DB data):
```bash
docker compose down -v
```

## Running the backend locally (optional)
If you want to run the Spring Boot app on your host and keep MySQL in Docker, you’ll need to expose port **3306** from the DB service (uncomment below), then use a `localhost` JDBC URL.


Run the app locally:
```bash
./gradlew bootRun
```

## Useful commands
- Shell into the DB container:
  ```bash
  docker compose exec db bash
  ```
- Quick MySQL check:
  ```bash
  docker compose exec db mysql -uadmin -padmin1234 -e "SELECT VERSION(); SHOW DATABASES;"
  ```
- View tables created by Hibernate:
  ```bash
  docker compose exec db mysql -uadmin -padmin1234 -e "USE commonplace; SHOW TABLES;"
  ```