# Prerequisites

- Docker installed and running
- Java 21 available on path

# Building

```
./gradlew buildDockerImage
```

# Running locally

```
docker-compose -f ./deployment/docker-compose.yaml up
```

Open http://localhost:8080

# Development

## IntelliJ IDEA

`New -> Project from existing sources (or version control) -> Choose import from external model / gradle`

Configure nodejs framework using `frontend/.gradle/nodejs/...` or your own nodejs.
