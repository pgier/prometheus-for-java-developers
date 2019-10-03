#!/bin/bash

pushd "$( dirname "${BASH_SOURCE[0]}" )"
javac SimpleHttpServer.java
java SimpleHttpServer &
ID=$!
popd
echo "Started HTTP server with PID=${ID}"
sleep 2
