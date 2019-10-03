# Prometheus for Java Developers

This repository contains some examples for using prometheus with Java applications.

## Simple HTTP with metrics textfile

The directory `http-textfile-metrics` contains a very simple Java HTTP server which serves
a metrics textfile which can be collected by Prometheus.  To run the example, first
start Prometheus using the example config file.

```
prometheus --config-file http-textfile-metrics/prometheus-config.yml
```

Then run the Java HTTP server and modify metrics.txt to see changes in prometheus.
```
http-textfile-metrics/run.sh
```

The example metrics will be available on `http://localhost:8080`, and the Prometheus web
interface will be available on `http://localhost:9090`.

## Examples using Kubernetes

### Prepare Kubernetes

Install [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/) or point kubectl at a running
instance of kubernetes.

To enable cluster metrics for minikube, enable the metrics-server addon.

```
  minikube addons enable metrics-server
```

### Minikube Requirements

The default minikube settings (2 cpus and 2GB of memory) should be enough to deploy just prometheus, but
more resources will be necessary when deploying 2 or more of the examples at the same time, and
additional disk space is necessary to store a longer duration of metrics.

```
  minikube start --vm-driver=kvm2 --cpus=3 --memory=6g --disk-size=60g
```

The service deployments in the examples are configured to use NodePorts for easy testing.  To enable use
of domain names instead of ip addresses, just add the IP of minikube to /etc/hosts

```
  minikube ip
```

### Install Prometheus into Kubernetes

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


