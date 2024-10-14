package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ElasticSearchIndexer {

    private static final String INDEX_NAME = "words";
    private ElasticSearchClient esClient;
    private ObjectMapper objectMapper = new ObjectMapper();

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

    public void indexWords() throws IOException {
        InputStream is = ElasticSearchIndexer.class.getClassLoader().getResourceAsStream("italian_cities.json");
        List<Map<String, Object>> words = objectMapper.readValue(is, new TypeReference<List<Map<String, Object>>>() {
        });

        for (Map<String, Object> word : words) {
            IndexRequest request = new IndexRequest(INDEX_NAME).source(word, XContentType.JSON);
            System.out.println("Inserting request " + request.toString());
            esClient.getClient().index(request, RequestOptions.DEFAULT);
        }
    }
}
