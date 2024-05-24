package edu.uci.ics.crawler4j.web;

import java.io.IOException;

import org.apache.http.HttpEntity;

import edu.uci.ics.crawler4j.tld.WebURL;


public interface Page {

     void load(HttpEntity entity, int maxBytes) throws IOException;

     static ParsedPage newPage(WebURL url) {
         return new ParsedPage(url);
     }

}
