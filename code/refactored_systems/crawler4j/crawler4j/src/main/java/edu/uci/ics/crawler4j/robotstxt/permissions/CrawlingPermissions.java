package edu.uci.ics.crawler4j.robotstxt.permissions;

import java.io.IOException;

import edu.uci.ics.crawler4j.config.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.tld.WebURL;

public interface CrawlingPermissions {
    boolean allows(WebURL webURL) throws IOException, InterruptedException;

    void setCrawlConfig(CrawlConfig crawlConfig);

    static CrawlingPermissions newCrawlingPermissions(RobotstxtConfig config, PageFetcher pageFetcher) {
        return new ServerBasedCrawlingPermissions(config, pageFetcher);
    }
}
