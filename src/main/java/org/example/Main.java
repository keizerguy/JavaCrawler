package org.example;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create reesults file
        BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));
        writer.write("Gecrawlde websites:");
        writer.close();

        // Start crawling
        CrawlController controller = getCrawlController();
        //controller.addSeed("https://explorista.nl/kopenhagen-duur-kosten/");
        controller.addSeed("https://en.wikipedia.org/wiki/Open-source_intelligence");
        controller.start(HtmlCrawler.class, 2); // Number of crawlers


        // Add results to database
//        try (ElasticSearchClient esClient = new ElasticSearchClient()) {
//
//            ElasticSearchIndexer indexer = new ElasticSearchIndexer(esClient);
//            indexer.createIndexIfNotExist();
//            indexer.indexWords();
//        }
    }

    private static CrawlController getCrawlController() throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("/data/crawl/root");
        config.setMaxDepthOfCrawling(2);
        config.setMaxPagesToFetch(3);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        return new CrawlController(config, pageFetcher, robotstxtServer);
    }
}