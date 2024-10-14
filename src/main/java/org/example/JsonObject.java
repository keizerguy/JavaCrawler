package org.example;

public class JsonObject {
    public static class MyJsonObject {
        private String url;
        private String title;
        private String[] keywords;

        public MyJsonObject(String url, String title, String[] keywords) {
            this.url = url;
            this.title = title;
            this.keywords = keywords;
        }
    }
}
