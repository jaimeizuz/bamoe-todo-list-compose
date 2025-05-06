mvn "-Pbamoe-community" "-Pbamoe-persistence" "-Pbamoe-jobs" "-Pbamoe-kafka-events" "-Popentelemetry-traces-logs-metrics" clean package

docker build -t scib-fund-onboarding-process-service:1.0.0 -f .\src\main\docker\Dockerfile.jvm .

docker-compose --profile bamoe-process-service --profile postgresql --profile wiremock `
  --profile messaging-redpanda `
  --profile bamoe-consoles --profile keycloak `
  --profile observability-jaeger `
  --profile observability-prometheus `
  --profile observability-elk `
  up -d

docker-compose --profile bamoe-process-service --profile postgresql --profile wiremock `
  --profile messaging-redpanda `
  --profile bamoe-consoles --profile keycloak `
  --profile observability-jaeger `
  up -d

docker-compose --profile observability-jaeger `
  --profile observability-prometheus `
  --profile observability-elk `
  up -d
  