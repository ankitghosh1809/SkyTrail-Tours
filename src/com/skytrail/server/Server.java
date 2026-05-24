package com.skytrail.server;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

/**
 * SkyTrail Tours — HTTP Server entry point.
 *
 * <p>Serves the static frontend and exposes the booking REST API.</p>
 *
 * <pre>
 *   Compile:  javac -d out $(find src -name "*.java")
 *   Run:      java -cp out com.skytrail.server.Server
 *   Open:     http://localhost:8080
 * </pre>
 */
public class Server {

    static final int    PORT     = 8080;
    static final String WEB_ROOT = "web";

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/api/book", new BookingHandler());
        server.createContext("/",         new StaticFileHandler(WEB_ROOT));
        server.setExecutor(null);
        server.start();

        System.out.println("=================================================");
        System.out.println("  SkyTrail Tours is running");
        System.out.println("  Open: http://localhost:" + PORT);
        System.out.println("=================================================");
    }
}
