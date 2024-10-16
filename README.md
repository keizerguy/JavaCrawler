# Abel's Java Crawler

A Java based web crawler built on Crawler4j and ElasticSearch, running on Docker.

## How to run
1. Starting the project.
   * On windows:
     * Execute ```./run.ps1```

   * On linux:
     * Execute ```docker-compose -f docker-compose.yml up --build```

2. Go to <localhost:8080>

3. Wait until the elasticsearch container is fully operational before pressing the 'Crawl' button. Otherwise the java app will crash due to a denied connection. 

4. Specify a URL to crawl (and optionally other parameters), and start crawling. 

Note: the 'Display Results' button displays all current results, even when the crawler is still active. Hence you might need to refresh the page to properly show all results once the crawler has finished visiting pages.


(Visit <http://localhost:5601/app/discover#/?_g=(filters:!(),refreshInterval:(pause:!t,value:0),time:(from:now-15m,to:now))&_a=(columns:!(),filters:!(),index:'09183990-8a3f-11ef-8703-c16bcb4c0eab',interval:auto,query:(language:kuery,query:''),sort:!())> to directly view the crawl results inside the ElasticSearch database, using Kibana.)