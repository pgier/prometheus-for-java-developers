FROM jboss/base-jdk:11

ADD jmx_prometheus_javaagent-0.12.0.jar /opt/
ADD jmx_config.yml /opt/
ADD target/jmx-example-jar-with-dependencies.jar /opt/

EXPOSE 8080 8081

CMD ["java", "-javaagent:/opt/jmx_prometheus_javaagent-0.12.0.jar=8081:/opt/jmx_config.yml", "-jar", "/opt/jmx-example-jar-with-dependencies.jar", "-Djava.net.preferIPv4Stack=true"]

