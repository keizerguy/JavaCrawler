package org.example.elasticsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

public class ElasticSearchIndexer {

    private static final String INDEX_NAME = "words";
    private ElasticSearchClient esClient;

    public ElasticSearchIndexer(ElasticSearchClient esClient) {
        this.esClient = esClient;
    }

    public void createIndexIfNotExist() throws IOException {
        if (!esClient.indexExists(INDEX_NAME)) {
            System.out.println("Index does not exist. Creating it.");
            esClient.getClient().indices().create(new CreateIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
        } else {
            System.out.println("Index already exists.");
        }
    }

    public void indexWords(String json) throws IOException {
        // Create an IndexRequest using the JSON string directly
        IndexRequest request = new IndexRequest(INDEX_NAME).source(json, XContentType.JSON);
        System.out.println("Inserting request " + request.toString());

        // Index the request
        esClient.getClient().index(request, RequestOptions.DEFAULT);
    }
}
