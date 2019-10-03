
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Basic HTTP server for textfile metrics
 */
public class SimpleHttpServer {
    public static final int HTTP_PORT = 8080;

    public static void main(String[] args) throws Exception {
        new SimpleHttpServer().run();
    }

    public void run() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
        server.createContext("/", httpExchange -> {
            handleDefault(httpExchange);
        });
        server.createContext("/metrics", httpExchange -> {
            handleMetrics(httpExchange);
        });
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("HTTP Server running on port: " + HTTP_PORT);
    }

    private void handleDefault(HttpExchange hx) throws IOException {
        String response = new String(Files.readAllBytes(Paths.get("index.html")));
        hx.sendResponseHeaders(200, response.length());
        OutputStream os = hx.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleMetrics(HttpExchange hx) throws IOException {
        String response = new String(Files.readAllBytes(Paths.get("metrics.txt")));
        hx.sendResponseHeaders(200, response.length());
        OutputStream os = hx.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}

