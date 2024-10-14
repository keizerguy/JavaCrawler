package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;

import static org.example.PageRank.extractKeywords;

public class HtmlCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");

    /**
     * Specify whether the given url should be crawled or not based on
     * the crawling logic. Here URLs with extensions css, js etc. will not be visited
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        System.out.println("shouldVisit: " + url.getURL().toLowerCase());

        String urlString = url.getURL().toLowerCase();
        return !FILTERS.matcher(urlString).matches();
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by the program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData htmlParseData) {
            String title = htmlParseData.getTitle();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("---------------------------------------------------------");
            System.out.println("Page URL: " + url);
            System.out.println("Text title: " + title);
            System.out.println("Number of outgoing links: " + links.size());
            System.out.println("---------------------------------------------------------");

            // Extract text from the html
            String extractedText = Jsoup.parse(html).text();

            // Extract the top N keywords
            List<Map.Entry<String, Double>> keywords = extractKeywords(extractedText, 10);

            // Display the results
            for (Map.Entry<String, Double> entry : keywords) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter("results.txt", true));
                writer.append('\n');
                writer.append("Page URL: ").append(url).append(", ").append("Text title: ").append(title).append(", ").append("Outgoing links: ").append(String.valueOf(links.size()));
                writer.append('\n');
                writer.append("Keywords:");
                for (Map.Entry<String, Double> entry : keywords) {
                    writer.append(entry.getKey()).append(" ");
                }
                writer.append('\n');
                writer.append("Extracted text:").append(extractedText);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}