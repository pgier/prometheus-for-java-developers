package com.example.jmx;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;

/** Basic HTTP server with Hit counter MBean */
public class MyHttpServer {
    public static final int HTTP_PORT = 8080;

    private HitCounter hits = new HitCounter();

    public static void main(String[] args) throws Exception {
        new MyHttpServer().run();
    }

    public void run() throws Exception {
        registerMBean();
        startServer();
    }

    private void registerMBean() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName hitsObjectName1 = new ObjectName("com.example.jmx:type=counter,name=hits,app=jmx");
        server.registerMBean(hits, hitsObjectName1);
    }

    private void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
        server.createContext("/", httpExchange -> {
            handle(httpExchange);
        });
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("HTTP Server running on port: " + HTTP_PORT);
    }

    private void handle(HttpExchange hx) throws IOException {
        hits.increment();
        String response = "Hello from JMX App!\n";
        hx.sendResponseHeaders(200, response.length());
        OutputStream os = hx.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}

