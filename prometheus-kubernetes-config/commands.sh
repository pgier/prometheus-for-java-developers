
# start minikube with kvm driver (for Fedora based systems)
# minikube --vm-driver=kvm2
#
# enable minikube metrics-server
# minikube addons enable metrics-server

alias k=kubectl

# Create the prometheus configmap
k apply -f prometheus-setup.yml

# Deploy Prometheus
k apply -f prometheus-deployment.yml

# Attach local port to prometheus-service.  Run in the background
# so that we can still run commands.
# This can be killed with `fg` followed by `ctrl-c`
# or something like `pkill kubectl`
k port-forward svc/prometheus-service 9090:9090 > /dev/null &


# deploy wildfly
k apply -f wildfly.yml

