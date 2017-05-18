#!/usr/bin/env bash
#docker-compose
curl -L https://github.com/docker/compose/releases/download/1.13.0/docker-compose-`uname -s`-`uname -m`     >~/docker-compose
chmod +x ~/docker-compose
sudo mv ~/docker-compose /usr/local/bin/docker-compose

#docker-machine
curl -L https://github.com/docker/machine/releases/download/v0.11.0/docker-machine-`uname -s`-`uname -m` >~/docker-machine &&
    chmod +x ~/docker-machine &&
    sudo mv ~/docker-machine /usr/local/bin/docker-machine