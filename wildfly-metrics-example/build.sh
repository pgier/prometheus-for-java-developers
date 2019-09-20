#!/bin/bash

mvn compile war:war

eval $(minikube docker-env)
docker build -t wildfly-metrics-example .
