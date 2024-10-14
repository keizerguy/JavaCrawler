package org.example;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create results file
        BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));
        writer.write("Gecrawlde websites:");
        writer.close();

        try (ElasticSearchClient esClient = new ElasticSearchClient()) {
            if (esClient.indexExists("words")){
                // Create the DeleteIndexRequest
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("words");

                // Execute the delete operation
                esClient.getClient().indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);

                System.out.println("Index 'words' deleted");
            }
        }

        // Start crawling
        CrawlController controller = getCrawlController();
        //controller.addSeed("https://explorista.nl/kopenhagen-duur-kosten/");
        controller.addSeed("https://en.wikipedia.org/wiki/Open-source_intelligence");
        controller.start(HtmlCrawler.class, 2); // Number of crawlers
    }

    private static CrawlController getCrawlController() throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("/data/crawl/root");
        config.setMaxDepthOfCrawling(3);
        config.setMaxPagesToFetch(50);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        return new CrawlController(config, pageFetcher, robotstxtServer);
    }
}