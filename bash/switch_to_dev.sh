#!/usr/bin/env bash

cd "$(dirname "$0")/.."

#docker-compose -f infra/docker-compose-tests.yml down
docker-compose -f infra/docker-compose.yml up -d
