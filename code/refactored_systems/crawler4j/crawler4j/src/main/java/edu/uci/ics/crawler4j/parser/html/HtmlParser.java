package edu.uci.ics.crawler4j.parser.html;

import edu.uci.ics.crawler4j.config.CrawlConfig;
import edu.uci.ics.crawler4j.web.ParsedPage;
import edu.uci.ics.crawler4j.parser.parse.html.HtmlParseData;
import edu.uci.ics.crawler4j.tld.TLDList;

public interface HtmlParser {

    HtmlParseData parse(ParsedPage page, String contextURL) throws ParseException;

    static HtmlParser newHtmlParser(CrawlConfig config, TLDList tldList) throws InstantiationException, IllegalAccessException  {
        return new TikaHtmlParser(config, tldList);
    }

}
