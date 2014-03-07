/*
 */
package org.schemaanalyst.util.xml;

import org.xml.sax.InputSource;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Reformats XML text for pretty-printing.
 *
 * @author Chris J. Wright
 */
public class XMLFormatter {

    /**
     * Reformats XML text for pretty-printing.
     *
     * @param xml The XML text.
     * @return The formatted XML text.
     */
    public static String format(String xml) {
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());
            serializer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (IllegalArgumentException | TransformerException | TransformerFactoryConfigurationError ex) {
            throw new RuntimeException("Failed to format XML", ex);
        }
    }
}
