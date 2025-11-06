# Threaded Backend

A Spring Boot backend with a MySQL database, packaged for Docker. This README explains how to build and run the app
using the provided Docker Compose setup.
> **TL;DR**
> 1) Build the backend image: `./gradlew bootBuildImage`
> 2) Push the image to your registry
> 3) Use the provided `docker-compose.yaml` in your frontend project to run the backend and database together.
---

## Prerequisites

- **Docker Desktop** (Compose v2; you should have the `docker-compose` command)
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
./gradlew bootBuildImage --imageName=threaded-backend:latest
```

## Push the image to your registry

To Push the image to your registry, first log in (You need a **Personal Access Token** for GitHub Container Registry):

```bash
docker login ghcr.io
```

Tag and push the image to your repository. (GitHub Container Registry example):

```bash
docker -t threaded-backend:latest ghcr.io/your-github-username/threaded-backend:latest
docker push ghcr.io/your-github-username/threaded-backend:latest
```

---

## Run with Docker Compose in your frontend project

Now if you want to run the backend with its database, you can use Docker Compose.
Add the following `docker-componse.yaml` file to your frontend project.
This file setup is known-good for this project. **Keep it exactly as-is** unless you know what you’re changing.

```yaml
services:
  db:
    image: mysql:latest
    environment:
      MYSQL_DATABASE: threaded
      MYSQL_ROOT_PASSWORD: 1234
      MYSQL_USER: admin
      MYSQL_PASSWORD: admin1234
    healthcheck:
      test: [ "CMD-SHELL","mysqladmin ping -h 127.0.0.1 -uroot -p1234 --silent" ]
      interval: 5s
      timeout: 3s
      retries: 20

  backend:
    image: ghcr.io/gamingarmorydev2nd/threaded-backend:v1.0.0
    pull_policy: always
    depends_on:
      db:
        condition: service_healthy
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/threaded?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: admin1234
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8080:8080"
```

Start the stack:

```bash
docker-compose up -d
```

Tail logs:

```bash
docker-compose logs -f db
# in a second terminal
docker-compose logs -f backend
```

Stop container:

```bash
docker-compose stop
```

```bash
docker-compose down
```
Reset the database (⚠️ deletes all DB data):

## Useful commands

- Shell into the DB container:
  ```bash
  docker-compose exec db bash
  ```
- Quick MySQL check:
  ```bash
  docker-compose exec db mysql -uadmin -padmin1234 -e "SELECT VERSION(); SHOW DATABASES;"
  ```
- View tables created by Hibernate:
  ```bash
  docker-compose exec db mysql -uadmin -padmin1234 -e "USE threaded; SHOW TABLES;"
  ```