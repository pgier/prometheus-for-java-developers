FROM jboss/base-jdk:11

ADD target/thorntail-example-thorntail.jar /opt/thorntail.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar",  "-Djava.net.preferIPv4Stack=true"]

