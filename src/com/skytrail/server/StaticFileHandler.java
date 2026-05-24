package com.skytrail.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Serves static files (HTML, CSS, JS, images) from the configured web root.
 * Protects against path-traversal attacks.
 */
public class StaticFileHandler implements HttpHandler {

    private final String webRoot;

    public StaticFileHandler(String webRoot) {
        this.webRoot = webRoot;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendError(exchange, 405, "Method Not Allowed");
            return;
        }

        String uri = exchange.getRequestURI().getPath();
        if (uri.equals("/") || uri.isEmpty()) uri = "/index.html";

        Path root = Paths.get(webRoot).toAbsolutePath().normalize();
        Path file = root.resolve(uri.substring(1)).normalize();

        if (!file.startsWith(root)) {
            sendError(exchange, 403, "Forbidden");
            return;
        }

        File f = file.toFile();
        if (!f.exists() || !f.isFile()) {
            sendError(exchange, 404, "Not found: " + uri);
            return;
        }

        byte[] content = Files.readAllBytes(file);
        exchange.getResponseHeaders().set("Content-Type", contentType(uri));
        exchange.sendResponseHeaders(200, content.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(content);
        }
    }

    private String contentType(String uri) {
        if (uri.endsWith(".html")) return "text/html; charset=UTF-8";
        if (uri.endsWith(".css"))  return "text/css; charset=UTF-8";
        if (uri.endsWith(".js"))   return "application/javascript; charset=UTF-8";
        if (uri.endsWith(".png"))  return "image/png";
        if (uri.endsWith(".jpg") || uri.endsWith(".jpeg")) return "image/jpeg";
        if (uri.endsWith(".svg"))  return "image/svg+xml";
        if (uri.endsWith(".ico"))  return "image/x-icon";
        return "application/octet-stream";
    }

    private void sendError(HttpExchange ex, int code, String msg) throws IOException {
        byte[] body = msg.getBytes("UTF-8");
        ex.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        ex.sendResponseHeaders(code, body.length);
        try (OutputStream out = ex.getResponseBody()) { out.write(body); }
    }
}
