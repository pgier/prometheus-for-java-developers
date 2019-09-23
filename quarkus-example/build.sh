#!/bin/bash

mvn package -Pnative

eval $(minikube docker-env)

docker build -t quarkus-example .
