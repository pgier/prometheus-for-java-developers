#!/bin/bash

# Create the prometheus configmap
kubectl apply -f prometheus-setup.yml

# Deploy Prometheus
kubectl apply -f prometheus-deployment.yml

# Optional, attach local port to prometheus-service.  Run in the background
# so that we can still run commands.
# This can be killed with `fg` followed by `ctrl-c`
# or something like `pkill kubectl`
#
# k port-forward svc/prometheus-service 9090:9090 > /dev/null &


