#!/bin/bash

mvn clean compile assembly:single

eval $(minikube docker-env)

docker build -t jmx-example .

