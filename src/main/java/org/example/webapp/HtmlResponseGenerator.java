package org.example.webapp;

public class HtmlResponseGenerator {

    /**
     * Generates an HTML response based on the given URL and parameters.
     *
     * @param url The URL to crawl.
     * @param param1 Number of crawlers.
     * @param param2 Crawling depth.
     * @param param3 Pages to visit.
     * @return A formatted HTML string displaying the crawl results.
     */
    public static String generateCrawlResultsPage(String url, int param1, int param2, int param3) {
        // Create a string to display the results
        String jsonResponse = String.format("{\"url\": \"%s\", \"param1\": %d, \"param2\": %d, \"param3\": %d}",
                url, param1, param2, param3);

        // Return the formatted HTML string
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Abel's Web Crawler - Results</title>"
                + "    <style>"
                + "        body {"
                + "            font-family: Arial, sans-serif;"
                + "            background-color: #f0f0f0;"
                + "            margin: 0;"
                + "            padding: 0;"
                + "            display: flex;"
                + "            flex-direction: column;"
                + "            align-items: center;"
                + "            justify-content: center;"
                + "            height: 100vh;"
                + "            position: relative;"
                + "        }"
                + "        .top-bar {"
                + "            background-color: #007bff;"
                + "            width: 100%;"
                + "            height: 50px;"
                + "            position: absolute;"
                + "            top: 0;"
                + "            left: 0;"
                + "            z-index: 10;"
                + "        }"
                + "        h1 {"
                + "            color: #333;"
                + "            margin-bottom: 20px;"
                + "        }"
                + "        .container {"
                + "            background-color: #fff;"
                + "            padding: 40px;"
                + "            border-radius: 8px;"
                + "            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);"
                + "            max-width: 90%;"
                + "            width: 500px;"
                + "            text-align: center;"
                + "            margin-top: 60px;"
                + "        }"
                + "        pre {"
                + "            background-color: #e9ecef;"
                + "            padding: 15px;"
                + "            border-radius: 5px;"
                + "            text-align: left;"
                + "            max-width: 100%;"
                + "            overflow-x: auto;"
                + "        }"
                + "        nav {"
                + "            background-color: transparent;"
                + "            padding: 10px;"
                + "            position: absolute;"
                + "            top: 10px;"
                + "            left: 10px;"
                + "            z-index: 20;"
                + "        }"
                + "        nav a {"
                + "            color: white;"
                + "            margin: 0 15px;"
                + "            text-decoration: none;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"top-bar\"></div>"
                + "    <nav>"
                + "        <a href=\"/\">Home</a>"
                + "    </nav>"
                + "    <div class=\"container\">"
                + "        <h1>Crawl Results</h1>"
                + "        <p>Crawl request results:</p>"
                + "        <pre>" + jsonResponse + "</pre>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }
}
