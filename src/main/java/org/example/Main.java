package org.example;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.example.elasticsearch.ElasticSearchClient;

import org.example.webapp.SimpleHttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        // Empty the database beforehand
        try (ElasticSearchClient esClient = new ElasticSearchClient()) {
            if (esClient.indexExists("words")){
                // Create the DeleteIndexRequest
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("words");

                // Execute the delete operation
                esClient.getClient().indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);

                System.out.println("Index 'words' deleted");
            }
        }

        // Run crawling server
        try {
            SimpleHttpServer server = new SimpleHttpServer(8080);
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}