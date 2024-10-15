package org.example.webapp;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

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
    public static String generateCrawlProgressPage(String url, int param1, int param2, int param3) {
        // Create a string to display the results
        String jsonResponse = String.format("{\"Page to Crawl\": \"%s\", \"Number of Crawlers\": %d, \"Crawling Depth\": %d, \"Pages to Visit\": %d}",
                url, param1, param2, param3);

        // Return the formatted HTML string with a button to display the results
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
                + "        form {"
                + "            margin-top: 20px;"
                + "        }"
                + "        button {"
                + "            padding: 10px 20px;"
                + "            background-color: #007bff;"
                + "            color: white;"
                + "            border: none;"
                + "            border-radius: 5px;"
                + "            cursor: pointer;"
                + "            font-size: 16px;"
                + "        }"
                + "        button:hover {"
                + "            background-color: #0056b3;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"top-bar\"></div>"
                + "    <nav>"
                + "        <a href=\"/\">Home</a>"
                + "    </nav>"
                + "    <div class=\"container\">"
                + "        <h1>Crawling...</h1>"
                + "        <p>Crawl request in progress:</p>"
                + "        <pre>" + jsonResponse + "</pre>"
                + "        <form action=\"/display-results\" method=\"POST\">"
                + "            <button type=\"submit\">Display Results</button>"
                + "        </form>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }

    public static String generateCrawlResultsPage(SearchResponse searchResponse) {
        // Start building the HTML response
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<!DOCTYPE html>")
                .append("<html lang=\"en\">")
                .append("<head>")
                .append("    <meta charset=\"UTF-8\">")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .append("    <title>Abel's Web Crawler - Results</title>")
                .append("    <style>")
                .append("        body {")
                .append("            font-family: Arial, sans-serif;")
                .append("            background-color: #f0f0f0;")
                .append("            margin: 0;")
                .append("            padding: 0;")
                .append("            display: flex;")
                .append("            flex-direction: column;")
                .append("            align-items: center;")
                .append("            justify-content: center;")
                .append("            height: 100vh;")
                .append("            position: relative;")
                .append("        }")
                .append("        .top-bar {")
                .append("            background-color: #007bff;")
                .append("            width: 100%;")
                .append("            height: 50px;")
                .append("            position: absolute;")
                .append("            top: 0;")
                .append("            left: 0;")
                .append("            z-index: 10;")
                .append("        }")
                .append("        .nav {")
                .append("            position: absolute;")
                .append("            top: 10px;")
                .append("            left: 10px;")
                .append("        }")
                .append("        h1 {")
                .append("            color: #333;")
                .append("            margin-bottom: 20px;")
                .append("        }")
                .append("        .container {")
                .append("            background-color: #fff;")
                .append("            padding: 40px;")
                .append("            border-radius: 8px;")
                .append("            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);")
                .append("            max-width: 90%;")
                .append("            width: 500px;")
                .append("            text-align: center;")
                .append("            margin-top: 60px;")
                .append("        }")
                .append("        table {")
                .append("            width: 100%;")
                .append("            border-collapse: collapse;")
                .append("            margin-top: 20px;")
                .append("        }")
                .append("        th, td {")
                .append("            border: 1px solid #ddd;")
                .append("            padding: 8px;")
                .append("            text-align: left;")
                .append("        }")
                .append("        th {")
                .append("            background-color: #007bff;")
                .append("            color: white;")
                .append("        }")
                .append("    </style>")
                .append("</head>")
                .append("<body>")
                .append("    <div class=\"top-bar\"></div>")
                .append("    <nav>")
                .append("        <a href=\"/\">Home</a>")
                .append("    </nav>")
                .append("    <div class=\"container\">")
                .append("        <h1>Crawl Results</h1>")
                .append("        <table>")
                .append("            <tr>")
                .append("                <th>URL</th>")
                .append("                <th>Title</th>")
                .append("                <th>Keywords</th>")
                .append("            </tr>");

        // Populate the table rows from the SearchHit array
        for (SearchHit hit : searchResponse.getHits()) {
            htmlResponse.append("            <tr>")
                    .append("                <td>").append(hit.getSourceAsMap().get("url")).append("</td>")
                    .append("                <td>").append(hit.getSourceAsMap().get("title")).append("</td>")
                    .append("                <td>").append(hit.getSourceAsMap().get("keywords")).append("</td>")
                    .append("            </tr>");
        }

        htmlResponse.append("        </table>")
                .append("    </div>")
                .append("</body>")
                .append("</html>");

        // Return the generated HTML response
        return htmlResponse.toString();
    }
}
