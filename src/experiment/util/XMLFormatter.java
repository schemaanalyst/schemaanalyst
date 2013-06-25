/*
 */
package experiment.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;

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
            Transformer serializer= SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Source xmlSource=new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
            StreamResult res =  new StreamResult(new ByteArrayOutputStream());            
            serializer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream)res.getOutputStream()).toByteArray());
        } 
	catch(IllegalArgumentException|TransformerException|TransformerFactoryConfigurationError ex){
            throw new RuntimeException("Failed to format XML", ex);
        }
    }
}
