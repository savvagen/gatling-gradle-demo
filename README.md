# Performance Testing POC for Gatling + Gradle
<img src="https://www.aukfood.fr/wp-content/uploads/2019/06/gatling-logo.png" width="200" height="180"></img>

## Setup Test Application (Json Server)

```
cd json_server
docker build -t savvagenchevskiy/json-server:latest -f Dockerfile .
### Run Json Server with Docker Compose
docker-compose up -d
```
Server will be available on: http://localhost:3001/

## Run Gatling simulations with Gradle
1. Run `LoadSimulation` only:
``` 
./gradlew clean gatlingRun
```

2. Run specific simulations:
``` 
./gradlew clean gatlingRun-org.example.simulations.LoadTestSimulation
```
More info in [Gatling Gradle plugin]("https://github.com/lkishalmi/gradle-gatling-plugin#default-tasks")

## Run Gatling in Docker container:
``` 
docker build -f Dckerfile -t gatling-distributed:latest

# then

docker run --rm -u gradle -e-v "$(pwd)":/home/gradle/test -w /home/gradle/test gatling-distributed:latest gradle wrapper; ./gradlew clean gatlingRun

```
