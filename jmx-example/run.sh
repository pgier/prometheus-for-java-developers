#!/bin/bash

java -javaagent:./jmx_prometheus_javaagent-0.12.0.jar=8081:jmx_config.yml -jar target/jmx-example-jar-with-dependencies.jar
