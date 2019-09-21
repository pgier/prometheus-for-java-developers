#!/bin/bash

mvn package

eval $(minikube docker-env)

docker build -t thorntail-example .
