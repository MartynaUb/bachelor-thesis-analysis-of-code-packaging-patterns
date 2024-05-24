package edu.uci.ics.crawler4j.web;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public interface UrlCanonicalizer {

    String getCanonicalURL(String url) throws UnsupportedEncodingException;

    String getCanonicalURL(String href, String context, Charset charset) throws UnsupportedEncodingException;

    String getCanonicalURL(String href, String context) throws UnsupportedEncodingException;

    void setHaltOnError(boolean haltOnError);

    static UrlCanonicalizer getUrlCanonicalizer() {
        return new DefaultURLCanonicalizer();
    }
}
