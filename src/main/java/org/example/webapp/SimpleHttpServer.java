package org.example.webapp;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.example.HtmlCrawler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpServer {

    private HttpServer server;

    public SimpleHttpServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Load the HTML file
                String response = new String(Files.readAllBytes(Paths.get("index.html")));
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.createContext("/crawl", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                if ("POST".equals(exchange.getRequestMethod())) {
                    // Read the request body
                    InputStream inputStream = exchange.getRequestBody();
                    String requestBody = new BufferedReader(new InputStreamReader(inputStream))
                            .lines()
                            .reduce("", (accumulator, actual) -> accumulator + actual);

                    // Parse the parameters
                    Map<String, String> parameters = parseParameters(requestBody);
                    String url = parameters.get("url");
                    String param1 = parameters.get("param1");
                    String param2 = parameters.get("param2");
                    String param3 = parameters.get("param3");

                    // Check if the URL is reachable
                    if (!isValidUrl(url)) {
                        String response = "Invalid URL: " + url + ". Please ensure the specified URL is correct.";
                        exchange.sendResponseHeaders(400, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        return;
                    }

                    // Generate a response
                    String response = String.format("Crawling URL: %s\nParameters: %s, %s, %s",
                            url, param1, param2, param3);

                    // Send the response back to the client
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();

                    // Start crawling with user specified input
                    try {
                        crawlWithParameters(url, stringToInt(param1, 24), stringToInt(param2, 1), stringToInt(param3, 100));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            }
        });
    }

    public void start() {
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + server.getAddress().getPort());
    }

    private Map<String, String> parseParameters(String requestBody) {
        Map<String, String> parameters = new HashMap<>();
        String[] pairs = requestBody.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                parameters.put(keyValue[0], java.net.URLDecoder.decode(keyValue[1], java.nio.charset.StandardCharsets.UTF_8));
            }
        }
        return parameters;
    }

    private boolean isValidUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD"); // Check URL without downloading content
            connection.setConnectTimeout(3000); // Timeout 3 seconds
            connection.connect();
            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400); // Check if response code is OK
        } catch (IOException e) {
            return false;
        }
    }

    public static int stringToInt(String param, int defaultValue) {
        try {
            if (param != null && !param.isEmpty()) {
                return Integer.parseInt(param);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid value for parameter, using default: " + e.getMessage());
        }
        return defaultValue; // Return the default value if parsing fails
    }


    private void crawlWithParameters(String url, int numberOfCrawlers, int crawlingDepth, int pagesToVisit) throws Exception {
        // Start crawling
        CrawlController controller = getCrawlController(crawlingDepth, pagesToVisit);
        controller.addSeed(url);
        controller.start(HtmlCrawler.class, numberOfCrawlers); // Number of crawlers
    }

    private static CrawlController getCrawlController(int crawlingDepth, int pagesToVisit) throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("/data/crawl/root");
        config.setPolitenessDelay(100);
        config.setMaxDepthOfCrawling(crawlingDepth);
        config.setMaxPagesToFetch(pagesToVisit);
        config.setThreadShutdownDelaySeconds(3);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        return new CrawlController(config, pageFetcher, robotstxtServer);
    }
}