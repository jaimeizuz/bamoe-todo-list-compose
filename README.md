# Kogito and Infrastructure services

## Option 1: Create and run Kogito App (for quarkus:dev execution)
1. In "kogito-app-example/kogito-bpmn-processes" folder, run `mvn "-Pbamoe-community" "-Pdevelopment" "-Pembedded-postgresql" "-Pbamoe-persistence" "-Pbamoe-jobs" "-Pbamoe-kafka-events" clean quarkus:dev`
2. Access the jBPM Dev-UI in [http://localhost:8080/q/dev-ui/org.jbpm.jbpm-quarkus-devui/process-instances]() to check everything is up and running.

## Option 2: Create and run Kogito App (for docker-compose)
1. ```docker compose --profile todolist-pgsql --profile kogito-management-console up -d```
2. ```docker compose --profile todolist-elasticsearch --profile kogito-management-console up -d```
3. ```docker compose --profile todolist-kafka --profile kogito-management-console up -d```
4. In the root folder "kogito-infra-services-minimal", run `docker compose up -d`.

These are the main web applications exposed in the docker-compose:
- Kogito Management Console: [http://localhost:8280/]()
- Kafka events console (based on Redpanda UI): [http://localhost:9001/]()
- PostgreSQL database: [postgresql://postgres:5432/kogito]() (user: kogito-user, password: kogito-pass)
- PGAdmin: [http://localhost:8055/]()

To start a process instance, just execute this HTTP request: \
```
    curl --location 'http://localhost:8080/hiring' \
        --header 'Accept: application/json' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "candidateData": {
                "name": "Jon",
                "lastName": "Snow",
                "email": "jon@snow.org",
                "experience": 5,
                "skills": ["Java", "Kogito", "Fencing"]
            },
            "needMgmtApproval" : false,
            "expirationTime" : "PT120S",
            "throwException" : false,
            "workitemType" : "ST",
            "errorStrategy" : "RETHROW"
            }'
```