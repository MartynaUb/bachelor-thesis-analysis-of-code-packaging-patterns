/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uci.ics.crawler4j.parser;

import java.util.Collections;
import java.util.HashSet;

import org.apache.tika.language.LanguageIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.parser.html.ParseException;
import edu.uci.ics.crawler4j.config.CrawlConfig;
import edu.uci.ics.crawler4j.web.ParsedPage;
import edu.uci.ics.crawler4j.parser.html.HtmlParser;
import edu.uci.ics.crawler4j.parser.parse.ParseData;
import edu.uci.ics.crawler4j.parser.parse.binary.BinaryParseData;
import edu.uci.ics.crawler4j.parser.parse.css.CssParseData;
import edu.uci.ics.crawler4j.parser.parse.html.HtmlParseData;
import edu.uci.ics.crawler4j.parser.parse.text.TextParseData;
import edu.uci.ics.crawler4j.tld.TLDList;
import edu.uci.ics.crawler4j.util.Net;
import edu.uci.ics.crawler4j.util.Util;

/**
 * @author Yasser Ganjisaffar
 */
public class Parser {

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    private final CrawlConfig config;

    private final HtmlParser htmlContentParser;

    private final Net net;

    @Deprecated
    public Parser(CrawlConfig config) throws IllegalAccessException, InstantiationException {
        this(config, HtmlParser.newHtmlParser(config, null));
    }

    public Parser(CrawlConfig config, TLDList tldList) throws IllegalAccessException, InstantiationException {
        this(config, HtmlParser.newHtmlParser(config, tldList), tldList);
    }

    @Deprecated
    public Parser(CrawlConfig config, HtmlParser htmlParser) {
        this(config, htmlParser, null);
    }

    public Parser(CrawlConfig config, HtmlParser htmlParser, TLDList tldList) {
        this.config = config;
        this.htmlContentParser = htmlParser;
        this.net = new Net(config, tldList);
    }

    public void parse(ParsedPage page, String contextURL) throws NotAllowedContentException, ParseException {
        if (Util.hasBinaryContent(page.getContentType())) { // BINARY
            ParseData parseData = new BinaryParseData();
            if (config.isIncludeBinaryContentInCrawling()) {
                if (config.isProcessBinaryContentInCrawling()) {
                    try {
                        parseData.setBinaryContent(page.getContentData());
                    } catch (Exception e) {
                        if (config.isHaltOnError()) {
                            throw new ParseException(e);
                        } else {
                            logger.error("Error parsing file", e);
                        }
                    }
                } else {
                    parseData.setContent("<html></html>");
                }
                page.setParseData(parseData);
                if (parseData.getContent() == null) {
                    throw new ParseException();
                }
                parseData.setOutgoingUrls(net.extractUrls(parseData.getContent()));
            } else {
                throw new NotAllowedContentException();
            }
        } else if (Util.hasCssTextContent(page.getContentType())) { // text/css
            try {
                ParseData parseData = new CssParseData();
                if (page.getContentCharset() == null) {
                    parseData.setContent(new String(page.getContentData()));
                } else {
                    parseData.setContent(new String(page.getContentData(), page.getContentCharset()));
                }
                parseData.setOutgoingUrls(new HashSet<>(Collections.singletonList(page.getWebURL())));
                page.setParseData(parseData);
            } catch (Exception e) {
                logger.error("{}, while parsing css: {}", e.getMessage(), page.getWebURL().getURL());
                throw new ParseException();
            }
        } else if (Util.hasPlainTextContent(page.getContentType())) { // plain Text
            try {
                ParseData parseData = new TextParseData();
                if (page.getContentCharset() == null) {
                    parseData.setContent(new String(page.getContentData()));
                } else {
                    parseData.setContent(new String(page.getContentData(), page.getContentCharset()));
                }
                parseData.setOutgoingUrls(net.extractUrls(parseData.getContent()));
                page.setParseData(parseData);
            } catch (Exception e) {
                logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL());
                throw new ParseException(e);
            }
        } else { // isHTML

            HtmlParseData parsedData = this.htmlContentParser.parse(page, contextURL);

            if (page.getContentCharset() == null) {
                page.setContentCharset(parsedData.getContentCharset());
            }

            // Please note that identifying language takes less than 10 milliseconds
            LanguageIdentifier languageIdentifier = new LanguageIdentifier(parsedData.getContent());
            page.setLanguage(languageIdentifier.getLanguage());

            page.setParseData(parsedData);

        }
    }
}
