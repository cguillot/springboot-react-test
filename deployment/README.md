# Build and deploy locally

From the current folder:

```shell
cd ..

./gradlew buildDockerImage

cd deployment
docker-compose up
```

Open http://localhost:8080
