BACKING_SERVICES=docker-compose.base.yml

start-dev:
	docker compose -f $(BACKING_SERVICES) up -d

down-dev:
	docker compose -f $(BACKING_SERVICES) down

restart-dev: down-dev start-dev