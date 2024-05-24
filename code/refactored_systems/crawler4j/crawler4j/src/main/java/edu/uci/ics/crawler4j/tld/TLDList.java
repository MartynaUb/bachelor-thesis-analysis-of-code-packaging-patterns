package edu.uci.ics.crawler4j.tld;

import java.io.IOException;

import edu.uci.ics.crawler4j.config.CrawlConfig;

public interface TLDList {


    boolean contains(String domain);

    boolean isRegisteredDomain(String domain);

    static TLDList fromFile(CrawlConfig config) throws IOException {
        return new TLDListFromFile(config);
    }
}
