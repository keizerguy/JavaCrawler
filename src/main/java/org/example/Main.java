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
        BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));
        writer.write("Gecrawlde websites:");
        writer.close();

        CrawlController controller = getCrawlController();
        controller.addSeed("https://explorista.nl/kopenhagen-duur-kosten/");
        controller.start(HtmlCrawler.class, 2); // Number of crawlers
    }

    private static CrawlController getCrawlController() throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("/data/crawl/root");
        config.setMaxDepthOfCrawling(2);
        config.setMaxPagesToFetch(100);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        return new CrawlController(config, pageFetcher, robotstxtServer);
    }
}