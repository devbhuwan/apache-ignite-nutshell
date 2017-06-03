#!/usr/bin/env bash
for X in ignite-node1
do
    heroku apps:destroy --app --confirm $X
    heroku apps:create $X
    heroku container:push web --app $X
done
