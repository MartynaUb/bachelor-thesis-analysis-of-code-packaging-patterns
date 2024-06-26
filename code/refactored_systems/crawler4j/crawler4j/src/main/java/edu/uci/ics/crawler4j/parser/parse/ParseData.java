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

package edu.uci.ics.crawler4j.parser.parse;

import java.io.IOException;
import java.util.Set;

import javax.xml.transform.TransformerConfigurationException;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import edu.uci.ics.crawler4j.tld.WebURL;

public interface ParseData {


    default void setBinaryContent(byte[] data)
        throws TransformerConfigurationException, TikaException, SAXException, IOException {
        setContent(new String(data));
    }

    String getContent();

    void setContent(String content);

    Set<WebURL> getOutgoingUrls();

    void setOutgoingUrls(Set<WebURL> outgoingUrls);

    @Override
    String toString();
}