# Prometheus for Java Developers

This repository contains some examples for using prometheus with Java applications.

## Prepare Kubernetes

Install [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/) or point kubectl at a running
instance of kubernetes.

### Minikube Requirements

The default minikube settings (2 cpus and 2GB of memory) should be enough to deploy just prometheus, but
more resources will be necessary when deploying 2 or more of the examples at the same time.

```
  minikube start --vm-driver=kvm2 --cpus=3 --memory=6g --disk-size=50g
```

## Install Prometheus

The kubernetes-config directory contains deployment configuration for Prometheus.

```
   kubectl apply -f prometheus-setup.yml
   kubectl apply -f prometheus-deployment.yml
```

## Deploy Wildfly with Very Simple Webapp

Change to the directory wildfly-metrics-webapp.

`cd wildfly-metrics-webapp`

Build the war file

` mvn compile war:war`

Build the Docker image using the minikube docker daemon

```
  eval $(minikube docker-env)
  docker build -t wildfly-metrics-webapp .
```


