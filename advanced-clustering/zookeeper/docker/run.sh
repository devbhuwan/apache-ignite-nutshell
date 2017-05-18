#!/usr/bin/env bash
rm -rf vol
mkdir vol vol/zk
docker-compose stop
docker-compose rm
docker-compose up
