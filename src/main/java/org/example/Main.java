package org.example;

import org.example.webapp.SimpleHttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        // Run crawling server
        try {
            SimpleHttpServer server = new SimpleHttpServer(8080);
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}