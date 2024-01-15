#!/bin/bash

if [ -z "$1" ]; then
  echo "Informe a tag para a imagem."
else
  ./build_docker_image $1 && docker-compose up -d --build --force-recreate
fi